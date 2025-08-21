package com.example.recipetags.repository;

import com.example.recipetags.model.RecipeTag;
import com.example.recipetags.model.RecipeTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, RecipeTagId> {
}
