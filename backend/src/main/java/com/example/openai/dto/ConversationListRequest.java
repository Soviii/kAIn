package com.example.openai.dto;

import jakarta.validation.constraints.NotNull;

public class ConversationListRequest {
    @NotNull
    private Integer recipeId;

    public ConversationListRequest() {}

    public Integer getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
}
