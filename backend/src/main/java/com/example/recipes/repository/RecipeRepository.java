package com.example.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.recipes.model.Recipe;
import java.util.UUID;
import java.util.List;


//note when we "extends jpaRepository" we get a lot of methods for free, like save, findById, deleteById, etc.
//we can also define our own methods here, like findByUserId
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findAllByUserId(Long userId);
}
