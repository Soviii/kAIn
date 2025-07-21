package com.example.recipes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.recipes.dto.RecipeRequestDTO;
import com.example.recipes.model.Recipe;
import com.example.recipes.service.RecipeService;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // TODO uncomment when ready to implement functions
    // public RecipeResponseDTO getRecipe(Long id) {
    //     return recipeService.getRecipeById(id);
    // }

    // TODO uncomment when ready to implement functions
    // public List<RecipeResponseDTO> getAllRecipes() {
    //     return recipeService.getAllRecipes();
    // }

    // @PostMapping
    // public RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequestDTO) {
    //     return recipeService.createRecipe(recipeRequestDTO);
    // }
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeRequestDTO recipeRequestDTO) {
        Recipe createdRecipe = recipeService.createRecipe(recipeRequestDTO);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    // TODO uncomment when ready to implement functions
    // public RecipeResponseDTO updateRecipe(Long id, RecipeRequestDTO recipeRequest) {
    //     return recipeService.updateRecipe(id, recipeRequest);
    // }

    // public void deleteRecipe(Long id) {
    //     recipeService.deleteRecipe(id);
    // }
}