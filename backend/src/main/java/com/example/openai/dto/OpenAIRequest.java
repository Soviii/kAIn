package com.example.openai.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenAIRequest {
    @NotNull
    private Integer recipeId;

    @NotBlank
    @JsonProperty("message")
    private String userMessage;

    // Default constructor (needed for deserialization)
    //   - variables assigned values when passed in from API Request (in OpenAIController)
    public OpenAIRequest() {}

    // Getters and setters
    public Integer getRecipeId() {
        return recipeId;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
