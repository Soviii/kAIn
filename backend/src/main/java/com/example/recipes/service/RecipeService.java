package com.example.recipes.service;

import com.example.recipes.dto.RecipeRequestDTO;
import com.example.recipes.dto.RecipeResponseDTO;

import com.example.ingredients.dto.IngredientRequestDTO;
import com.example.ingredients.dto.IngredientResponseDTO;
import com.example.ingredients.model.Ingredient;

import com.example.steps.dto.StepRequestDTO;
import com.example.steps.dto.StepResponseDTO;
import com.example.steps.model.Step;

import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Create a new Recipe (POST /recipes)
     */
    // TODO Figure out why recipe_id is set to null in db and add documentation to all files
    public RecipeResponseDTO createRecipe(RecipeRequestDTO dto) {
        // 1) Map DTO → Entity
        Recipe recipe = new Recipe();
        recipe.setUserId(dto.getUserId());
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());

        List<Ingredient> ingredients = dto.getIngredients().stream()
            .map(iDto -> {
                Ingredient i = new Ingredient();
                i.setName(iDto.getName());
                i.setQuantity(iDto.getQuantity());
                i.setRecipe(recipe);
                i.setUnit(iDto.getUnit());
                return i;
            })
            .collect(Collectors.toList());
        recipe.setIngredients(ingredients);

        List<Step> steps = dto.getSteps().stream()
            .map(sDto -> {
                Step s = new Step();
                s.setInstruction(sDto.getInstruction());
                s.setStepNumber(sDto.getStepNumber());
                s.setRecipe(recipe);
                return s;
            })
            .collect(Collectors.toList());
        recipe.setSteps(steps);

        // 2) Persist (cascades ingredients & steps)
        Recipe saved = recipeRepository.save(recipe);

        // 3) Map Entity → Response DTO
        RecipeResponseDTO response = new RecipeResponseDTO();
        // response.setRecipeUuid(saved.getRecipeUuid());
        response.setRecipeId(saved.getRecipeId());
        response.setUserId(saved.getUserId());
        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setIngredients(
            saved.getIngredients().stream()
                 .map(i -> new IngredientResponseDTO(
                     i.getId(), 
                     i.getName(), 
                     i.getQuantity()))
                 .collect(Collectors.toList())
        );
        response.setSteps(
            saved.getSteps().stream()
                 .map(s -> new StepResponseDTO(
                     s.getId(), 
                     s.getInstruction(), 
                     s.getStepNumber()))
                 .collect(Collectors.toList())
        );

        return response;
    }

    // (other CRUD methods would go here…)
}
