package com.example.recipes.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.config.JwtConfig;
import com.example.recipes.dto.DeleteRecipeDTO;
import com.example.recipes.dto.RecipeDetailsDTO;
import com.example.recipes.dto.RecipeRequestDTO;
import com.example.recipes.dto.RecipeResponseDTO;
import com.example.recipes.service.RecipeService;
import com.example.recipes.dto.RecipeSummaryDTO;
import com.example.recipes.dto.UpdateRecipeRequestDTO;
import com.example.recipes.dto.UpdateRecipeResponseDTO;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final JwtConfig jwtConfig;

    public RecipeController(RecipeService recipeService, JwtConfig jwtConfig) {
        this.recipeService = recipeService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@CookieValue(value = "kAIn-jwt", required = false) String token, @RequestBody RecipeRequestDTO recipeRequestDTO, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);
        Long userId = Long.parseLong(jwtConfig.getUserId(token));

        RecipeResponseDTO savedRecipe = recipeService.createRecipe(recipeRequestDTO, userId);

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
    public ResponseEntity<List<RecipeSummaryDTO>> getRecipes(@CookieValue(value = "kAIn-jwt", required = false) String token, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);
        Long userId = Long.parseLong(jwtConfig.getUserId(token));

        List<RecipeSummaryDTO> recipes = recipeService.getRecipes(userId);
        return ResponseEntity.ok(recipes);
    }

    // Retrieves details for specific recipe (doesn't return user id)
    @GetMapping("/details")
    public ResponseEntity<RecipeDetailsDTO> getRecipeDetails(@CookieValue(value = "kAIn-jwt", required = false) String token, @RequestHeader("recipeId") Long recipeId, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);
        Long userId = Long.parseLong(jwtConfig.getUserId(token));
        
        return ResponseEntity.ok(this.recipeService.getRecipeDetails(userId, recipeId));
    }

    // Deletes passed in recipeId
    @DeleteMapping
    public ResponseEntity<DeleteRecipeDTO> deleteRecipe(@CookieValue(value = "kAIn-jwt", required = false) String token, @RequestHeader("recipeId") Long recipeId, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);
        Long userId = Long.parseLong(jwtConfig.getUserId(token));
        
        return ResponseEntity.ok(this.recipeService.deleteRecipe(userId, recipeId));
    }

    // update recipe with new details
    @PatchMapping
    public ResponseEntity<UpdateRecipeResponseDTO> updateRecipe(@CookieValue(value = "kAIn-jwt", required = false) String token, @Valid @RequestBody UpdateRecipeRequestDTO newRecipeInfo, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);
        
        return ResponseEntity.ok(this.recipeService.updateRecipe(newRecipeInfo.getRecipeId(), newRecipeInfo));
    }
}