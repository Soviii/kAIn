// controller/OpenAIController.java
package com.example.openai.controller;

import com.example.openai.dto.OpenAIRequest;
import com.example.openai.dto.OpenAIResponse;
import com.example.openai.service.OpenAIService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }


    /**
     * Handles chat interactions between users and the AI assistant for recipe-related queries.
     *
     * @param req The chat request containing:
     *               - recipeId: The ID of the recipe being discussed
     *               - userMessage: The user's message or question
     * @return OpenAIResponse object:
     *         - aiMsg: AI assistant's response
     *         - status: HTTP status code (200 for success, 400 for invalid food-related queries)
     * @throws jakarta.validation.ConstraintViolationException if recipeId is null or userMessage is blank
     */
    @PostMapping("/userchat")
    public OpenAIResponse chatWithRecipeAssistant(@Valid @RequestBody OpenAIRequest req) {
        Integer recipeId = req.getRecipeId();
        String userMessage = req.getUserMessage();
        return this.openAIService.sendUserMessage(recipeId, userMessage);
    }
}
