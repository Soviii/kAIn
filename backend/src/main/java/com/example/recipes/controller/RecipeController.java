package com.example.recipes.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.recipes.dto.RecipeRequestDTO;
import com.example.recipes.dto.RecipeResponseDTO;
import com.example.recipes.model.Recipe;
import com.example.recipes.service.RecipeService;


@RestController
@RequestMapping("/recipes")
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
    public ResponseEntity<RecipeResponseDTO> createRecipe(@RequestBody RecipeRequestDTO recipeRequestDTO) {
        RecipeResponseDTO savedRecipe = recipeService.createRecipe(recipeRequestDTO);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()             // http://localhost:8080/recipes
            .path("/{id}")
            .buildAndExpand(savedRecipe.getRecipeId())
            .toUri();

        return ResponseEntity
            .created(location)                // 201 Created + Location: /recipes/{uuid}
            .body(savedRecipe);                   // JSON body = your RecipeResponseDTO
    
    }

    // TODO uncomment when ready to implement functions
    // public RecipeResponseDTO updateRecipe(Long id, RecipeRequestDTO recipeRequest) {
    //     return recipeService.updateRecipe(id, recipeRequest);
    // }

    // public void deleteRecipe(Long id) {
    //     recipeService.deleteRecipe(id);
    // }
}