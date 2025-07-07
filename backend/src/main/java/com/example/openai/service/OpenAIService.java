package com.example.openai.service;

import com.example.openai.model.OpenAIMessage;
import com.example.openai.dto.OpenAIResponse;
import com.openai.client.OpenAIClient;
import com.example.openai.repository.OpenAIMessageRepository;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpenAIService {

    private final OpenAIClient openAiClient;
    private final OpenAIMessageRepository openAIRepository;

    public OpenAIService(OpenAIClient openAiClient, OpenAIMessageRepository openAIRepository) {
        this.openAiClient = openAiClient;
        this.openAIRepository = openAIRepository;
    }


    /**
     * Determines if a user's message is related to food topics using OpenAI's classification.
     * 
     * This method sends the message to GPT-3.5-turbo with specific instructions to classify
     * whether the content is related to:
     * - Food
     * - Cooking
     * - Ingredients
     * - Recipes
     * - Dietary restrictions
     *
     * @param userMessage The message to be classified
     * @return boolean true if the message is food-related, false otherwise
     * @throws RuntimeException if the OpenAI API call fails
     */
    private boolean isRelevantFoodMessage(String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .addSystemMessage("You are a classifier that determines if a message is related to food, cooking, ingredients, recipes, or dietary restrictions. " +
                             "Respond with only 'true' or 'false'.")
            .addUserMessage(userMessage)
            .model(ChatModel.GPT_3_5_TURBO)
            .build();
    
        ChatCompletion completion = openAiClient.chat().completions().create(params);
        
        String response = completion.choices().stream()
            .findFirst()
            .flatMap(choice -> choice.message().content())
            .orElse("false");
        
        // Returns true or false based on GPT output
        return Boolean.parseBoolean(response.trim().toLowerCase());
    }

    /**
     * Processes a user's message about recipes and returns an AI-generated response.
     * This method:
     * 1. Validates if the message is food-related
     * 2. Sends the message to OpenAI's GPT model
     * 3. Stores both user message and AI response in the database
     *
     * @param recipeId The ID of the recipe being discussed
     * @param userMessage The user's question or comment about the recipe
     * @return OpenAIResponse containing:
     *         - aiMsg: AI's response to the user's message
     *         - status: 200 for successful responses
     *                  400 if message is not food-related
     * @throws jakarta.validation.ConstraintViolationException if recipeId is null or userMessage is blank
     * @throws RuntimeException if OpenAI API call fails
     */
    @Transactional
    public OpenAIResponse sendUserMessage(Integer recipeId, String userMessage) {
        // If not a food-related message, then return
        if (isRelevantFoodMessage(userMessage) == false) {
            return new OpenAIResponse("I can only assist with food-related inquiries.", 400);
        }

        // Prepare request to send message to ChatGPT model
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .addUserMessage(userMessage)
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

        return new OpenAIResponse(aiResponse, 200);
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


