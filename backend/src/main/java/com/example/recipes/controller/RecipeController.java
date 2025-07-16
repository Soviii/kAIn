package com.example.recipes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.recipes.dto.RecipeResponseDTO;
import com.example.recipes.service.RecipeService;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public RecipeResponseDTO getRecipe(Long id) {
        return recipeService.getRecipeById(id);
    }

    public List<RecipeResponseDTO> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    public RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequest) {
        return recipeService.createRecipe(recipeRequest);
    }

    public RecipeResponseDTO updateRecipe(Long id, RecipeRequestDTO recipeRequest) {
        return recipeService.updateRecipe(id, recipeRequest);
    }

    public void deleteRecipe(Long id) {
        recipeService.deleteRecipe(id);
    }
}