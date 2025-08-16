package com.example.recipes.service;

import com.example.recipes.dto.DeleteRecipeDTO;
import com.example.recipes.dto.RecipeDetailsDTO;
import com.example.recipes.dto.RecipeRequestDTO;
import com.example.recipes.dto.RecipeResponseDTO;
import com.example.recipes.dto.RecipeSummaryDTO;
import com.example.recipes.dto.UpdateRecipeRequestDTO;
import com.example.recipes.dto.UpdateRecipeResponseDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final StepsRepository stepsRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
            StepsRepository stepsRepository) {
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
        // .save() function is built into the RecipeRepository interface because it
        // extends JpaRepository
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
                                i.getQuantity(),
                                i.getUnit()))
                        .collect(Collectors.toList()));
        response.setSteps(
                saved.getSteps().stream()
                        .map(s -> new StepResponseDTO(
                                s.getId(),
                                s.getInstruction(),
                                s.getStepNumber()))
                        .collect(Collectors.toList()));

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
     * Validates that the recipe exists for the given user ID and recipe ID
     * Fetches the associated ingredients and steps from the database
     * Converts entities into their corresponding response DTOs
     * 
     *
     * @param userId   the ID of the user who owns the recipe
     * @param recipeId the ID of the recipe to retrieve
     * @return a RecipeDetailsDTO containing recipe details, ingredients, and steps
     * @throws ResponseStatusException if the recipe does not exist for the given
     *                                 user
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
                    stepDTOs);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Did not find recipe details with recipe id of " + recipeId + " and user ID of " + userId);
        }
        return existingRecipe;
    }

    /**
     * Deletes a recipe along with its associated steps and ingredients for a given
     * user.
     *
     * This method performs the following operations
     * 
     * Deletes all steps associated with the recipe using
     * {@code stepsRepository.deleteByRecipeId(recipeId)}
     * Deletes all ingredients associated with the recipe using
     * {@code ingredientRepository.deleteByRecipeId(recipeId)}
     * Deletes the recipe itself using
     * {@code recipeRepository.deleteByUserIdAndId(userId, recipeId)}
     * 
     *
     * If no recipe is found for the given {@code userId} and {@code recipeId}, a
     * {@link org.springframework.web.server.ResponseStatusException} with
     * {@link org.springframework.http.HttpStatus#NOT_FOUND} is thrown.
     *
     * @param userId   the ID of the user who owns the recipe
     * @param recipeId the ID of the recipe to delete
     * @return a {@link DeleteRecipeDTO} representing the result of the delete
     *         operation
     * @throws org.springframework.web.server.ResponseStatusException if the recipe
     *                                                                does not exist
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

    /**
     * Updates an existing recipe with new steps and ingredients information.
     *
     * This method performs a full update for a recipe:
     * - Updates existing steps and ingredients.
     * - Adds new steps and ingredients.
     * - Deletes any steps or ingredients that are missing from the incoming request.
     *
     * The operation is transactional, so all changes are committed together or rolled back in case of an error.
     *
     * @param recipeId the ID of the recipe to update
     * @param newRecipeInfo the DTO containing updated recipe details including steps and ingredients
     * @return an UpdateRecipeResponseDTO containing the updated recipe details
     *
     * @throws ResponseStatusException if the recipe with the given ID is not found
     *
     * Behavior:
     * 1. Steps:
     *    - Fetch all existing steps for the recipe.
     *    - Map existing steps by ID for quick lookup.
     *    - For each incoming step:
     *       - If the step ID exists, update the instruction and step number.
     *       - If the step ID is null, create a new Step entity linked to the recipe.
     *    - Delete any existing steps not present in the incoming list.
     *    - Save all updated and new steps.
     *
     * 2. Ingredients:
     *    - Fetch all existing ingredients for the recipe.
     *    - Map existing ingredients by ID for quick lookup.
     *    - For each incoming ingredient:
     *       - If the ingredient ID exists, update its name, quantity, and unit.
     *       - If the ingredient ID is null, create a new Ingredient entity linked to the recipe.
     *    - Delete any existing ingredients not present in the incoming list.
     *    - Save all updated and new ingredients.
     *
     * 3. **Response**:
     *    - Collect the updated steps and ingredients into their respective response DTOs.
     *    - Return an UpdateRecipeResponseDTO containing the recipe ID, title, description, updated ingredients, and updated steps.
     */
    // TODO: look over logic for steps and ingredients... works but make sure it's not just deleting
        // make sure it's using ID to compare, haven't clarified yet
    @Transactional
    public UpdateRecipeResponseDTO updateRecipe(Long recipeId, UpdateRecipeRequestDTO newRecipeInfo) {
        List<StepResponseDTO> stepUpdates = newRecipeInfo.getSteps()
            .stream()
            .map(s -> new StepResponseDTO(s.getId(), s.getInstruction(), s.getStepNumber()))
            .toList();

        List<IngredientResponseDTO> ingredientUpdates = newRecipeInfo.getIngredients()
            .stream()
            .map(i -> new IngredientResponseDTO(i.getId(), i.getName(), i.getQuantity(), i.getUnit()))
            .toList();

    
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        /* ---------- Steps ---------- */
        List<Step> existingSteps = stepsRepository.findAllByRecipeId(recipeId);
        System.out.println("current number of steps is:" + existingSteps.size());
        Map<Long, Step> stepMap = existingSteps.stream()
                .collect(Collectors.toMap(Step::getId, s -> s));

        // Update & add
        List<Step> updatedSteps = new ArrayList<>();
        for (StepResponseDTO dto : stepUpdates) {
            if (dto.getId() != null && stepMap.containsKey(dto.getId())) {
                Step step = stepMap.get(dto.getId());
                step.setStepNumber(dto.getStepNumber());
                step.setInstruction(dto.getInstruction());
                updatedSteps.add(step);
            } else { // new step
                Step newStep = new Step();
                newStep.setRecipe(recipe);
                newStep.setStepNumber(dto.getStepNumber());
                newStep.setInstruction(dto.getInstruction());
                updatedSteps.add(newStep);
            }
        }

        // Delete missing
        Set<Long> incomingStepIds = stepUpdates.stream()
                .map(StepResponseDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        existingSteps.stream()
                .filter(s -> !incomingStepIds.contains(s.getId()))
                .forEach(stepsRepository::delete);

        stepsRepository.saveAll(updatedSteps);

        /* ---------- Ingredients ---------- */
        List<Ingredient> existingIngredients = ingredientRepository.findAllByRecipeId(recipeId);
        Map<Long, Ingredient> ingredientMap = existingIngredients.stream()
                .collect(Collectors.toMap(Ingredient::getId, i -> i));

        // Update & add
        List<Ingredient> updatedIngredients = new ArrayList<>();
        for (IngredientResponseDTO dto : ingredientUpdates) {
            if (dto.getId() != null && ingredientMap.containsKey(dto.getId())) {
                Ingredient ing = ingredientMap.get(dto.getId());
                ing.setName(dto.getName());
                ing.setQuantity(dto.getQuantity());
                ing.setUnit(dto.getUnit());
                updatedIngredients.add(ing);
            } else { // new ingredient
                Ingredient newIng = new Ingredient();
                newIng.setRecipe(recipe);
                newIng.setName(dto.getName());
                newIng.setQuantity(dto.getQuantity());
                newIng.setUnit(dto.getUnit());
                updatedIngredients.add(newIng);
            }
        }

        // Delete missing
        Set<Long> incomingIngredientIds = ingredientUpdates.stream()
                .map(IngredientResponseDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        existingIngredients.stream()
                .filter(i -> !incomingIngredientIds.contains(i.getId()))
                .forEach(ingredientRepository::delete);

        ingredientRepository.saveAll(updatedIngredients);

        /* ---------- Response ---------- */
        List<StepResponseDTO> savedSteps = updatedSteps.stream()
                .map(s -> new StepResponseDTO(s.getId(), s.getInstruction(), s.getStepNumber()))
                .toList();

        List<IngredientResponseDTO> savedIngredients = updatedIngredients.stream()
                .map(i -> new IngredientResponseDTO(i.getId(), i.getName(), i.getQuantity(), i.getUnit()))
                .toList();

        return new UpdateRecipeResponseDTO(
                recipeId,
                recipe.getTitle(),
                recipe.getDescription(),
                savedIngredients,
                savedSteps);
    }

}
