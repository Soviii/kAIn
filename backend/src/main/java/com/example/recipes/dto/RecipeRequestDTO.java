package com.example.recipes.dto;

import java.util.List;

import com.example.ingredients.dto.IngredientRequestDTO;
import com.example.steps.dto.StepRequestDTO;

public class RecipeRequestDTO {
    // Declare fields
    private String title;
    private String description;
    private List<IngredientRequestDTO> ingredients;
    private List<StepRequestDTO> steps;

    // Default constructor
    public RecipeRequestDTO() {
        // Default constructor required for some frameworks
    }

    // Parameterized constructor
    public RecipeRequestDTO(String title, String description, List<IngredientRequestDTO> ingredients, List<StepRequestDTO> steps) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters and setters
    // These methods are used to access and modify the fields of the RecipeRequestDTO class
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

    public List<IngredientRequestDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRequestDTO> ingredients) { 
        this.ingredients = ingredients;
    }

    public List<StepRequestDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepRequestDTO> steps) {
        this.steps = steps;
    }
}