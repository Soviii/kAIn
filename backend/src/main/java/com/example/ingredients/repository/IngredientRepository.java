package com.example.ingredients.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.ingredients.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByRecipeId(Long recipeId);

    @Modifying
    int deleteByRecipeId(Long recipeId);
}
