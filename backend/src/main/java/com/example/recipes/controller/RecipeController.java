package com.example.recipes.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.recipes.dto.RecipeRequestDTO;
import com.example.recipes.dto.RecipeResponseDTO;
import com.example.recipes.service.RecipeService;
import com.example.recipes.dto.RecipeSummaryDTO;


@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@RequestBody RecipeRequestDTO recipeRequestDTO) {
        RecipeResponseDTO savedRecipe = recipeService.createRecipe(recipeRequestDTO);

        // 201 Created response with Location header pointing to the new resource
        // ServletUriComponentsBuilder helps build the URI for the newly created resource
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()             // http://localhost:8080/recipes
            .path("/{id}")
            .buildAndExpand(savedRecipe.getRecipeId())
            .toUri();

        // Return ResponseEntity with status 201 Created and the location of the new resource
        return ResponseEntity
            .created(location)                // 201 Created + Location: /recipes/{uuid}
            .body(savedRecipe);               // JSON body = your RecipeResponseDTO
    
    }

    // TODO FIGURE OUT WAY TO USE JWT/SESSION COOKIE TO GET USER ID INSTEAD OF PASSING IT IN AS A HEADER
    @GetMapping
    public ResponseEntity<List<RecipeSummaryDTO>> getRecipes(@RequestHeader("user-id") Long userId) {
        List<RecipeSummaryDTO> recipes = recipeService.getRecipes(userId);
        return ResponseEntity.ok(recipes);
    }
}