package com.example.recipes.service;

import com.example.recipes.dto.DeleteRecipeDTO;
import com.example.recipes.dto.RecipeDetailsDTO;
import com.example.recipes.dto.RecipeRequestDTO;
import com.example.recipes.dto.RecipeResponseDTO;
import com.example.recipes.dto.RecipeSummaryDTO;

import com.example.ingredients.dto.IngredientResponseDTO;
import com.example.ingredients.model.Ingredient;
import com.example.ingredients.repository.IngredientRepository;
import com.example.steps.dto.StepResponseDTO;
import com.example.steps.model.Step;
import com.example.steps.repository.StepsRepository;

import jakarta.transaction.Transactional;

import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final StepsRepository stepsRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, StepsRepository stepsRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.stepsRepository = stepsRepository;
    }

    /**
     * Create a new Recipe (POST /recipes)
     */
    public RecipeResponseDTO createRecipe(RecipeRequestDTO dto) {
        // 1) Map DTO → Recipe Entity
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
        // .save() function is built into the RecipeRepository interface because it extends JpaRepository
        // which provides basic CRUD operations
        Recipe saved = recipeRepository.save(recipe);

        // 3) Map Entity → Recipe Response DTO
        RecipeResponseDTO response = new RecipeResponseDTO();
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

    public List<RecipeSummaryDTO> getRecipes(Long userId) {
        // gets all recipes for a specific user
        List<Recipe> recipes = recipeRepository.findAllByUserId(userId);

        // maps the Recipe entities to RecipeSummaryDTOs
        return recipes.stream()
            .map(r -> {
                RecipeSummaryDTO dto = new RecipeSummaryDTO();
                dto.setRecipeId(r.getRecipeId());
                dto.setTitle(r.getTitle());
                dto.setDescription(r.getDescription());
                return dto;
            })
            .collect(Collectors.toList());
    }

    /**
     * Retrieves detailed information for a specific recipe owned by a given user.
     * 
     * This method:
     * 
     *     Validates that the recipe exists for the given user ID and recipe ID
     *     Fetches the associated ingredients and steps from the database
     *     Converts entities into their corresponding response DTOs
     * 
     *
     * @param userId   the ID of the user who owns the recipe
     * @param recipeId the ID of the recipe to retrieve
     * @return a RecipeDetailsDTO containing recipe details, ingredients, and steps
     * @throws ResponseStatusException if the recipe does not exist for the given user
     */
    public RecipeDetailsDTO getRecipeDetails(Long userId, Long recipeId) {
        /* TODO: validate user using JWT or server side sessions */

        // find recipe
        Optional<Recipe> recipe = recipeRepository.findByUserIdAndId(userId, recipeId);
        RecipeDetailsDTO existingRecipe;

        // if found matching recipe
        if (recipe.isPresent()) {
            Recipe r = recipe.get();
            // Get ingredients and convert to DTOs
            List<IngredientResponseDTO> ingredientDTOs = ingredientRepository.findAllByRecipeId(recipeId)
                .stream()
                .map(IngredientResponseDTO::new)
                .collect(Collectors.toList());

            // Get steps and convert to DTOs
            List<StepResponseDTO> stepDTOs = stepsRepository.findAllByRecipeId(recipeId)
                .stream()
                .map(StepResponseDTO::new)
                .collect(Collectors.toList());
            
            existingRecipe = new RecipeDetailsDTO(
                r.getRecipeId(),
                r.getTitle(),
                r.getDescription(),
                ingredientDTOs,
                stepDTOs
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Did not find recipe details with recipe id of " + recipeId + " and user ID of " + userId);
        }
        return existingRecipe;
    }

    /**
     * Deletes a recipe along with its associated steps and ingredients for a given user.
     *
     * This method performs the following operations
     * 
     *     Deletes all steps associated with the recipe using {@code stepsRepository.deleteByRecipeId(recipeId)}
     *     Deletes all ingredients associated with the recipe using {@code ingredientRepository.deleteByRecipeId(recipeId)}
     *     Deletes the recipe itself using {@code recipeRepository.deleteByUserIdAndId(userId, recipeId)}
     * 
     *
     * If no recipe is found for the given {@code userId} and {@code recipeId}, a
     * {@link org.springframework.web.server.ResponseStatusException} with {@link org.springframework.http.HttpStatus#NOT_FOUND} is thrown.
     *
     * @param userId   the ID of the user who owns the recipe
     * @param recipeId the ID of the recipe to delete
     * @return a {@link DeleteRecipeDTO} representing the result of the delete operation
     * @throws org.springframework.web.server.ResponseStatusException if the recipe does not exist
     */
    @Transactional
    public DeleteRecipeDTO deleteRecipe(Long userId, Long recipeId) {
        /* TODO: verify session with server side rendering and */
        DeleteRecipeDTO deleteRecipeDTO;
        stepsRepository.deleteByRecipeId(recipeId);
        ingredientRepository.deleteByRecipeId(recipeId);
        
        int deletedCount = recipeRepository.deleteByUserIdAndId(userId, recipeId);
        if (deletedCount == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }

        deleteRecipeDTO = new DeleteRecipeDTO(); // will initially return nothing unless other devs mention something...
        return deleteRecipeDTO;
    }
}
