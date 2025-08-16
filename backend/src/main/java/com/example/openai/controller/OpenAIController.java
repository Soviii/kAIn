// controller/OpenAIController.java
package com.example.openai.controller;

import com.example.openai.dto.OpenAIRequest;
import com.example.openai.dto.OpenAIResponse;
import com.example.config.JwtConfig;
import com.example.openai.dto.ConversationListRequest;
import com.example.openai.dto.ConversationListResponse;

import com.example.openai.service.OpenAIService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    private final OpenAIService openAIService;
    private final JwtConfig jwtConfig;

    public OpenAIController(OpenAIService openAIService, JwtConfig jwtConfig) {
        this.openAIService = openAIService;
        this.jwtConfig = jwtConfig;
    }


    /**
     * Retrieves the conversation history for a specific recipe.
     * 
     * This endpoint returns all messages exchanged between the user and AI about a recipe,
     * ordered chronologically from oldest to newest.
     *
     * @param req ConversationListRequest containing:
     *            - recipeId: The ID of the recipe to get conversation history for
     * @return ConversationListResponse containing:
     *         - messages: Array of messages in the conversation
     *         - Each message includes:
     *           * sender: Either "user" or "ai"
     *           * messageText: Content of the message
     *           * timestamp: When the message was sent
     * @throws ResponseStatusException (404) if no conversation found for the recipeId
     * @throws ConstraintViolationException if recipeId is null
     */
    @GetMapping("/getchat")
    public ConversationListResponse recipeConversation(@CookieValue(value = "kAIn-jwt", required = false) String token, @Valid ConversationListRequest req, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);

        Integer recipeId = req.getRecipeId();
        return this.openAIService.getRecipeConversation(recipeId);
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
    public OpenAIResponse chatWithRecipeAssistant(@CookieValue(value = "kAIn-jwt", required = false) String token, @Valid @RequestBody OpenAIRequest req, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);

        Integer recipeId = req.getRecipeId();
        String userMessage = req.getUserMessage();
        return this.openAIService.sendUserMessage(recipeId, userMessage);
    }
}
