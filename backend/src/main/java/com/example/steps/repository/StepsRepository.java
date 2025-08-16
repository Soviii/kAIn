package com.example.steps.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.steps.model.Step;
import java.util.List;

public interface StepsRepository extends JpaRepository<Step, Long>  {
    List<Step> findAllByRecipeId(Long recipeId);
    List<Step> findByRecipeId(Long recipeId);

    @Modifying
    int deleteByRecipeId(Long recipeId);
}

