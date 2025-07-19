package com.example.recipes.dto;

import java.util.List;
import com.example.ingredients.dto;
import com.example.steps.dto;

public class RecipeRequestDTO {
    private Long userId;
    private String title;
    private String description;
    private List<IngredientRequestDTO> ingredients;
    private List<StepRequestDTO> steps;

    public RecipeRequestDTO() {
    }

    public RecipeRequestDTO(Long userId, String title, String description, List<IngredientRequestDTO> ingredients, List<StepRequestDTO> steps) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

