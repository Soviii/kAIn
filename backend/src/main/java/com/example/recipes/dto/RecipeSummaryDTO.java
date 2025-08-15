package com.example.recipes.dto;

public class RecipeSummaryDTO {
    // Declare fields
    private Long recipeId;
    private String title;
    private String description;
    
    // Default constructor 
    public RecipeSummaryDTO() {
        // Default constructor required for some frameworks
    }

    // Getters and setters
    // These methods are used to access the fields of the RecipeSummaryDTO class
    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }   

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
