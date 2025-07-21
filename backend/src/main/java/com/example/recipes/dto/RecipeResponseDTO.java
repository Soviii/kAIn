package com.example.recipes.dto;

import java.util.List;
import java.util.UUID;
import com.example.ingredients.dto.IngredientResponseDTO;
import com.example.steps.dto.StepResponseDTO;

public class RecipeResponseDTO {
    private UUID recipeUuid;
    private Long userId;
    private String title;
    private String description;
    private List<IngredientResponseDTO> ingredients;
    private List<StepResponseDTO> steps;

    public RecipeResponseDTO() {
    }
    public RecipeResponseDTO(UUID recipeUuid, Long userId, String title, String description,
                             List<IngredientResponseDTO> ingredients, List<StepResponseDTO> steps) {
        this.recipeUuid = recipeUuid;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }
    // getters & setters omitted for brevity

    public UUID getRecipeUuid() {
        return recipeUuid;
    }

    public void setRecipeUuid(UUID recipeUuid) {
        this.recipeUuid = recipeUuid;
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