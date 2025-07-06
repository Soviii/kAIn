// package com.example.openai.service;

// import com.example.openai.model.OpenAIMessage;
// import com.example.openai.repository.OpenAIMessageRepository;
// import com.openai.client.OpenAIClient;
// import com.openai.models.ChatModel;
// import com.openai.models.chat.completions.ChatCompletion;
// import com.openai.models.chat.completions.ChatCompletionCreateParams;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// public class OpenAIService {

//     private final OpenAIClient openAiClient;
//     private final OpenAIMessageRepository repository;

//     public OpenAIService(OpenAIClient openAiClient, OpenAIMessageRepository repository) {
//         this.openAiClient = openAiClient;
//         this.repository = repository;
//     }

//     /**
//      * Sends the user message to OpenAI, saves the exchange, and returns the AI response.
//      */
//     @Transactional
//     public String chat(String userMessage) {
//         ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
//             .addUserMessage(userMessage)
//             .model(ChatModel.GPT_3_5_TURBO)
//             .build();

//         ChatCompletion completion = openAiClient.chat().completions().create(params);

//         String aiResponse = completion.choices().stream()
//             .findFirst()
//             .flatMap(choice -> choice.message().content())
//             .orElse("⚠️ Empty response from OpenAI");

//         // Save to DB
//         OpenAIMessage message = new OpenAIMessage(userMessage, aiResponse);
//         repository.save(message);

//         return aiResponse;
//     }
// }






/* FOR TESTING API KEY - SIMPLE TEST */
package com.example.openai.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

/**
 * Minimal service used only to verify the OPENAI_API_KEY and receive a reply.
 */
@Service
public class OpenAIService {

    private final OpenAIClient openAiClient;

    public OpenAIService(OpenAIClient openAiClient) {
        this.openAiClient = openAiClient;
    }

    /**
     * Sends the user's message to GPT‑3.5‑turbo and returns the first response.
     *
     * @param userMessage text the caller wants to send to the model
     * @return the assistant’s reply, or a fallback message if nothing comes back
     */
    public String chat(String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(userMessage)
                .model(ChatModel.GPT_3_5_TURBO)   // change if you have GPT‑4 access
                .build();

        ChatCompletion completion = openAiClient.chat()
                                               .completions()
                                               .create(params);

        // unwrap Optional<String> safely
        return completion.choices().stream()
                .findFirst()                                // first choice, if present
                .flatMap(choice -> choice.message().content()) // Optional<String>
                .orElse("⚠️  Empty response from OpenAI");
    }
}


