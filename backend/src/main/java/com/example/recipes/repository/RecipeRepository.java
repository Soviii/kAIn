package com.example.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.recipes.model.Recipe;
import java.util.UUID;
import java.util.List;
import java.util.Optional;


//note when we "extends jpaRepository" we get a lot of methods for free, like save, findById, deleteById, etc.
//we can also define our own methods here, like findByUserId
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findAllByUserId(Long userId);
    Optional<Recipe> findByUserIdAndId(Long userId, Long id);

    @Modifying // necessary for delete, insert, or patch
    int deleteByUserIdAndId(Long userId, Long id);
}
