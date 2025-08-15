package com.example.recipes.dto;

import java.util.List;

import com.example.ingredients.dto.IngredientResponseDTO;
import com.example.steps.dto.StepResponseDTO;

public class RecipeDetailsDTO {
     // Declare fields
    private Long recipeId;
    private String title;
    private String description;
    private List<IngredientResponseDTO> ingredients;
    private List<StepResponseDTO> steps;

    // Default constructor
    public RecipeDetailsDTO() {
        // Default constructor required for some frameworks
    }

    // Parameterized constructor
    public RecipeDetailsDTO(Long recipeId, String title, String description,
                             List<IngredientResponseDTO> ingredients, List<StepResponseDTO> steps) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters and setters
    // These methods are used to access and modify the fields of the RecipeResponseDTO class
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

    public List<IngredientResponseDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientResponseDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepResponseDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepResponseDTO> steps) {
        this.steps = steps;
    }
}
