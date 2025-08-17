package com.example.openai.service;

import com.example.openai.model.OpenAIMessage;
import com.example.openai.dto.OpenAIResponse;
import com.example.openai.dto.ConversationListResponse;
import com.openai.client.OpenAIClient;
import com.example.openai.repository.OpenAIMessageRepository;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.example.openai.model.ChatMessageDTO;
import com.example.openai.model.ChatSummary;
import com.example.openai.model.ChatSummaryDTO;
import com.example.openai.repository.SummaryRepository;
import com.example.recipes.dto.RecipeDetailsDTO;
import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.Duration;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.cache.CacheManager;

@Service
public class OpenAIService {

    private final OpenAIClient openAiClient;
    private final OpenAIMessageRepository openAIRepository;
    private final SummaryRepository summaryRepository;
    private final RecipeRepository recipeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public OpenAIService(OpenAIClient openAiClient,
            OpenAIMessageRepository openAIRepository,
            SummaryRepository summaryRepository,
            RecipeRepository recipeRepository,
            CacheManager cacheManager,
            RedisTemplate<String, Object> redisTemplate) {
        this.openAiClient = openAiClient;
        this.openAIRepository = openAIRepository;
        this.summaryRepository = summaryRepository;
        this.recipeRepository = recipeRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Determines whether a given user message (serialized as JSON context) is relevant to a recipe.
     * <p>
     * The method leverages OpenAI's GPT model to analyze the context of the recipe—including
     * its title, ingredients, steps, prior summaries, and the last 10 chat messages—to decide
     * if the user’s message relates to the recipe. It sends a system prompt that enforces strict
     * output rules:
     * <ul>
     *   <li>Returns <code>true</code> if the message is related to the recipe, ingredients, cooking methods,
     *       preparation steps, substitutions, portion sizes, garnishes, presentation, or any
     *       modifications involving food context.</li>
     *   <li>Returns <code>false</code> if the message is completely unrelated (e.g., personal questions,
     *       external topics, or unrelated shopping).</li>
     *   <li>Ambiguous cases default to <code>true</code> if any food or recipe-related connection exists.</li>
     *   <li>The AI is instructed to respond strictly with <code>"true"</code> or <code>"false"</code>, without extra text.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Implementation details:
     * <ul>
     *   <li>Sends the serialized recipe context JSON as the user message.</li>
     *   <li>Receives a single completion from the AI model.</li>
     *   <li>Parses the AI’s response string into a boolean. If parsing fails, defaults to <code>false</code>.</li>
     *   <li>Catches and logs exceptions, returning <code>false</code> in error cases.</li>
     * </ul>
     * </p>
     *
     * @param recipeContextJson JSON string containing recipe details, last summaries, and the last 10 messages.
     * @return {@code true} if the user’s question is food/recipe-related; {@code false} otherwise.
     */
    private boolean isRelevantFoodMessage(String recipeContextJson) {
        try {
            // Send to AI as a system message
            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                // TODO: update given payload to not include IDs and remove ID sentence in system message
                .addSystemMessage("""
                    Determine whether the user’s question is related to the provided recipe, including:
                    Title, ingredients, cooking steps, or cooking techniques
                    Ingredient usage, substitutions, flavor combinations, toppings, garnishes, portion adjustments, or presentation
                    Cooking times, temperatures, or method choices
                    Consider context from the last summaries (each summarizing 10 previous messages) and the last 10 messages to understand follow-ups, clarifications, or ongoing conversation threads.
                    Ignore any data fields or key-value pairs containing 'id'.
                    Treat a question as irrelevant (respond 'false') only if it is completely unrelated to the recipe, ingredients, preparation, or cooking context—e.g., unrelated personal questions, shopping questions not tied to the recipe, or external topics.
                    Respond strictly with 'true' or 'false'. Do not provide explanations, examples, or extra text.
                    Edge case guidance:
                    Questions about any ingredient use—even optional or unconventional—are relevant.
                    Questions that involve modifying, combining, or adding ingredients (like toppings or garnishes) are relevant.
                    Ambiguous or partially related questions should default to 'true' if any connection to ingredients, cooking steps, or preparation exists.
                """)
                .addUserMessage(recipeContextJson)
                .model(ChatModel.GPT_3_5_TURBO)
                .build();
            ChatCompletion completion = openAiClient.chat().completions().create(params);

            // returns list of List<ChatChoice> (usually will be one choice), converts List into a Stream to access each ChatChoice
            // findFirst() returns first element Optional<ChatChoice>
            // choice.message().content() returns Optional<String> (text from AI)
            // flatMap unwraps nested optional safely (in case choice or content is missing)
            // if no message present, then "false"
            String response = completion.choices().stream()
                .findFirst()
                .flatMap(choice -> choice.message().content())
                .orElse("false");

            return Boolean.parseBoolean(response.trim().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Handles a new user message in the recipe conversation context.
     *
     * Workflow:
     * 1. Validate that the recipe exists.
     * 2. Gather context:
     *    - Recipe details
     *    - Last 6 messages
     *    - Last 1 summary (for conversation continuity)
     *    - Current user message
     * 3. Serialize context into JSON and build a prompt for the AI.
     * 4. Send prompt to OpenAI (ChatGPT) with detailed instructions about tone and behavior.
     * 5. Get AI response and persist both user and AI messages into the database.
     * 6. Update the recipe's message count and trigger summary creation if needed.
     * 7. Store message in cache
     * 8. Return the AI’s response wrapped in an {@link OpenAIResponse}.
     *
     * @param recipeId    the ID of the recipe the conversation belongs to
     * @param userMessage the message text provided by the user
     * @return an {@link OpenAIResponse} containing the AI-generated reply and a status code
     * @throws ResponseStatusException if the recipe does not exist or payload serialization fails
    */
    @Transactional
    public OpenAIResponse sendUserMessage(Long recipeId, String userMessage) {
        Optional<Recipe> rOpt = recipeRepository.findById(recipeId);
        if (!rOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find recipe with id: " + recipeId);
        }
        Recipe currRecipe = rOpt.get();
        RecipeDetailsDTO currRecipeDetails = new RecipeDetailsDTO(currRecipe);
        List<ChatMessageDTO> last6Messages = openAIRepository.findLastMessages(recipeId, 6)
            .stream()
            .map(m -> new ChatMessageDTO(m))
            .toList();

        List<ChatSummaryDTO> last5Summaries = summaryRepository.findLastSummaries(recipeId, 1) // TODO: decide whether to do multiple summaries or only save 1... might not need much information 
            .stream()
            .map(s -> new ChatSummaryDTO(s))
            .toList();

        String recipeContextJson;
        // tries to store recipe details, last 6 messages, last summaries, and current message into a json and prompt GPT to answer 'true' or 'false'
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            // Create a payload object containing recipe and last messages
            Map<String, Object> payload = new HashMap<>();
            payload.put("recipe", currRecipeDetails);
            payload.put("lastSummaries", last5Summaries);
            payload.put("lastMessages", last6Messages);
            payload.put("currentMessage", userMessage);

            // Convert payload to JSON string
            recipeContextJson = mapper.writeValueAsString(payload);

            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            System.out.println("Prompt to AI (pretty JSON):\n" + prettyJson);

            // If not a food-related message, then return
            if (isRelevantFoodMessage(recipeContextJson) == false) {
                return new OpenAIResponse("I can only assist with food-related inquiries.", 400);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to serialize payload to JSON");
        }


        // Prepare request to send message to ChatGPT model
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .addSystemMessage("""
                You are a culinary assistant designed to help users improve, troubleshoot, or ask questions about recipes, ingredients, cooking techniques, meal planning, and related culinary topics. Your primary goal is to provide practical, clear, and logical guidance while enhancing the user’s cooking experience. Utilize the recipe details, last messages, and last summaries to get context regarding the previous conversation - this is important.

                Critical Evaluation:
                    Carefully read all user input and assess its accuracy, feasibility, and logic.
                    Identify inconsistencies, errors, or unclear instructions in recipes or questions.
                    Politely correct mistakes and explain why something may not work as written.
                    Suggest alternative solutions or improvements when appropriate, including ingredient substitutions, cooking methods, timing adjustments, or plating ideas.
                    Always provide reasoning for your recommendations to help the user learn and understand your guidance.
                    Take into consideration the last messages but don't mention them unless the user mentions them.
                    Mainly focus on the current message and use the other context if clarifying details are needed.
                    Ignore any data fields or key-value pairs containing 'id'.

                Feedback and Guidance:
                    Provide actionable, step-by-step advice when explaining techniques or adjustments.
                    When reviewing a recipe, comment on clarity, ingredient proportions, cooking times, flavor balance, and overall practicality.
                    Encourage safe and efficient cooking practices where relevant.
                    Offer tips for improving efficiency, taste, or presentation without overwhelming the user.
                    Adapt your advice logically based on additional information, corrections, or clarifications provided by the user.
                    Treat each prompt as if they are all separate prompts unless the user is specifically talking about a message from before.

                Writing Style and Tone:
                    Use clear, direct language that is easy to understand (Flesch reading score 80+).
                    Write in active voice.
                    Avoid unnecessary adverbs and avoid marketing, buzzword, or overly enthusiastic language.
                    Use culinary jargon only when it enhances clarity or understanding.
                    Maintain a calm, confident, professional tone. Be honest but supportive.
                    Balance critique with encouragement; make corrections constructive rather than discouraging.
                    Keep answers concise, ideally 3-5 sentences, unless a longer explanation is clearly warranted to provide clarity or important guidance.
                    Avoid use any '*' characters, instead use paragraphs/newlines to separate categories and numbers to list steps, details, etc.
                    Prioritize actionable, logical advice over verbosity.

                User Experience Focus:
                    Anticipate possible follow-up questions or challenges the user might face.
                    Be patient and responsive, providing explanations when the user asks for clarification.
                    Make guidance practical and easy to implement in real kitchen settings.
                    Prioritize clarity and logical reasoning over brevity; ensure the user fully understands your advice.
                    Always consider the user’s goals, context, and constraints, such as available ingredients, tools, or time.
                    Your ultimate objective is to help users cook successfully, learn new skills, and improve their recipes in a logical, understandable, and supportive way.
            """)
            .addUserMessage(recipeContextJson)
            .model(ChatModel.GPT_3_5_TURBO)
            .build();

        // Calls OpenAI API and gets AI response
        ChatCompletion completion = openAiClient.chat().completions().create(params);

        // Extract OpenAI ChatGPT response
        String aiResponse = completion.choices().stream()
            .findFirst()
            .flatMap(choice -> choice.message().content())
            .orElse("Empty response from OpenAI");

        // Save user message to database
        OpenAIMessage userMessageForDb = new OpenAIMessage(recipeId, "user", userMessage);
        openAIRepository.save(userMessageForDb);

        // Save AI response to database
        OpenAIMessage aiMessageForDb = new OpenAIMessage(recipeId, "ai", aiResponse);
        openAIRepository.save(aiMessageForDb);

        // add two messages to count list
        currRecipe.increaseMessageCount(2L);
        recipeRepository.save(currRecipe);

        // save new messages in cache
        // cacheNewMessages(userMessageForDb, aiMessageForDb, recipeId);
        String cacheKey = "conversationByRecipeId::" + recipeId;
        ConversationListResponse cachedConvo = (ConversationListResponse) redisTemplate.opsForValue().get(cacheKey);
        if (cachedConvo != null) {
            cachedConvo.addMessage(userMessageForDb);
            cachedConvo.addMessage(aiMessageForDb);
            redisTemplate.opsForValue().set(cacheKey, cachedConvo, Duration.ofMinutes(5));
        }

        createNewSummaryIfNecessary(currRecipe);

        return new OpenAIResponse(aiResponse, 200);
    }

    /**
     * Creates a new summary for the given recipe if the number of messages
     * reaches a multiple of 10. This ensures summaries are generated periodically
     * as the conversation grows, instead of on every single message.
     *
     * @param recipe the recipe entity whose messages are being tracked
     */
    private void createNewSummaryIfNecessary(Recipe recipe) {
        Long numOfMessages = recipe.getMessageCount();
        if (numOfMessages % 10 == 0) createSummary(recipe);
    }

    /**
     * Generates or updates a conversation summary for the given recipe.
     *
     * Workflow:
     * 1. Fetch the last 10 messages for the recipe.
     * 2. Serialize them into JSON (using Jackson) to provide context.
     * 3. Send the payload to OpenAI with a summarization system prompt.
     * 4. Retrieve the AI-generated summary from the response.
     * 5. Check if a summary already exists for this recipe:
     *    - If no summary exists, create a new one.
     *    - If one exists, update the most recent summary with the new text.
     * 6. Persist the summary using the summary repository.
     *
     * @param recipe the recipe entity whose conversation is being summarized
     * @throws ResponseStatusException if JSON serialization fails
     */
    private void createSummary(Recipe recipe) {
        List<ChatMessageDTO> last10Messages = openAIRepository.findLastMessages(recipe.getRecipeId(), 10)
                .stream()
                .map(m -> new ChatMessageDTO(m))
                .toList();
        String recipeContextJson;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            // Create a payload object containing recipe and last messages
            Map<String, Object> payload = new HashMap<>();
            payload.put("messages", last10Messages);
            recipeContextJson = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to serialize payload to JSON");
        }

        // Send to AI as a system message
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            // TODO: update given payload to not include IDs and remove ID sentence in
            // system message
            .addSystemMessage("""
                You are an AI assistant specialized in summarization.
                Your task is to read up to 10 messages from a conversation and create a coherent summary that consolidates the key points, context, and relevant details.
                Present the summary in one or more paragraphs as needed for clarity.
                Focus on the main ideas and important context; omit minor details, filler, and repetition.
                Ensure that the resulting summary is easy to read and provides enough information for someone to understand the conversation without reviewing the original messages.
            """)
            .addUserMessage(recipeContextJson)
            .model(ChatModel.GPT_3_5_TURBO)
            .build();
        ChatCompletion completion = openAiClient.chat().completions().create(params);

        // returns list of List<ChatChoice> (usually will be one choice), converts List
        // into a Stream to access each ChatChoice
        // findFirst() returns first element Optional<ChatChoice>
        // choice.message().content() returns Optional<String> (text from AI)
        // flatMap unwraps nested optional safely (in case choice or content is missing)
        // if no message present, then ""
        String response = completion.choices()
            .stream()
            .findFirst()
            .flatMap(choice -> choice.message().content())
            .orElse("");
        
        List<ChatSummary> summaries = summaryRepository.findLastSummaries(recipe.getRecipeId(), 1);
        ChatSummary newSummary;
        if (summaries.isEmpty()) {
            newSummary = new ChatSummary(recipe.getRecipeId(), response);
        } else {
            newSummary = summaries.get(0);   
            newSummary.setSummaryText(response);
        }
        summaryRepository.save(newSummary);
    }

    /**
     * Retrieves the conversation history for a specific recipe from the database.
     * Messages are returned in chronological order (oldest to newest).
     *
     * @param recipeId The ID of the recipe whose conversation history to retrieve
     * @return ConversationListResponse containing:
     *         - messages: List of OpenAIMessage objects in chronological order
     *         - Each message contains:
     *           * sender: Either "user" or "ai"
     *           * messageText: The content of the message
     * @throws ResponseStatusException with HTTP 404 if no messages found for the recipe
     */
    // instead of manually checking cache in code, can use annotation below
    // @Cacheable(
    //     cacheNames = "conversationByRecipeId",
    //     key = "#recipeId"
    // )
    @Transactional(readOnly = true)
    public ConversationListResponse getRecipeConversation(Long recipeId) {
        String cacheKey = "conversationByRecipeId::" + recipeId;
        ConversationListResponse cachedConvo = (ConversationListResponse) redisTemplate.opsForValue().get(cacheKey);
        if (cachedConvo != null) {
            System.out.println("in cache");
            redisTemplate.opsForValue().set(cacheKey, cachedConvo, Duration.ofMinutes(5));
            return cachedConvo;
        }

        ConversationListResponse convo = new ConversationListResponse();

        List<OpenAIMessage> dbMessages = openAIRepository.findByRecipeIdOrderByTimestampAsc(recipeId);

        if (dbMessages.size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No conversation found for recipeId: " + recipeId);
        }

        dbMessages.forEach(convo::addMessage); // for each OpenAIMessage in dbMessages, run convo.addMessage();
        redisTemplate.opsForValue().set(cacheKey, convo, Duration.ofMinutes(5));
        return convo;
    }
}






// /* FOR TESTING API KEY - SIMPLE TEST */
// package com.example.openai.service;

// import com.openai.client.OpenAIClient;
// import com.openai.models.ChatModel;
// import com.openai.models.chat.completions.ChatCompletion;
// import com.openai.models.chat.completions.ChatCompletionCreateParams;
// import org.springframework.stereotype.Service;

// /**
//  * Minimal service used only to verify the OPENAI_API_KEY and receive a reply.
//  */
// @Service
// public class OpenAIService {

//     private final OpenAIClient openAiClient;

//     public OpenAIService(OpenAIClient openAiClient) {
//         this.openAiClient = openAiClient;
//     }

//     /**
//      * Sends the user's message to GPT‑3.5‑turbo and returns the first response.
//      *
//      * @param userMessage text the caller wants to send to the model
//      * @return the assistant’s reply, or a fallback message if nothing comes back
//      */
//     public String chat(String userMessage) {
//         ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
//                 .addUserMessage(userMessage)
//                 .model(ChatModel.GPT_3_5_TURBO)   // change if you have GPT‑4 access
//                 .build();

//         ChatCompletion completion = openAiClient.chat()
//                                                .completions()
//                                                .create(params);

//         // unwrap Optional<String> safely
//         return completion.choices().stream()
//                 .findFirst()                                // first choice, if present
//                 .flatMap(choice -> choice.message().content()) // Optional<String>
//                 .orElse("⚠️  Empty response from OpenAI");
//     }
// }
