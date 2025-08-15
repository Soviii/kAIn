package com.example.steps.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.steps.model.Step;
import java.util.List;

public interface StepsRepository extends JpaRepository<Step, Long>  {
    List<Step> findAllByRecipeId(Long recipeId);
}

