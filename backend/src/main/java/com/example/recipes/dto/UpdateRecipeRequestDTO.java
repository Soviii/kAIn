package com.example.recipes.dto;

import java.util.List;

import com.example.ingredients.dto.IngredientResponseDTO;
import com.example.steps.dto.StepResponseDTO;
import com.example.tags.dto.TagRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateRecipeRequestDTO {
    @NotNull
    private Long recipeId;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private List<IngredientResponseDTO> ingredients;

    @NotNull
    private List<StepResponseDTO> steps;

    private List<TagRequestDTO> tags;

    public UpdateRecipeRequestDTO() {} // JPA requirement 

    public UpdateRecipeRequestDTO(Long recipeId, String title, String description, List<IngredientResponseDTO> ingredients, List<StepResponseDTO> steps, List<TagRequestDTO> tags) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.tags = tags;
    }

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

    public List<TagRequestDTO> getTags() {
        return this.tags;
    }

    public void setTags(List<TagRequestDTO> tags) {
        this.tags = tags;
    }
}
