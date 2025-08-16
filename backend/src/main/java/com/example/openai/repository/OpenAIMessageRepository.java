package com.example.openai.repository;

import com.example.openai.model.OpenAIMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OpenAIMessageRepository extends JpaRepository<OpenAIMessage, Long> {
    // Spring Data JPA will automatically implement this method
    // It will generate SQL based on the method name:
    // - findBy: SELECT operation
    // - RecipeId: WHERE recipe_id = ?
    // - OrderByTimestampAsc: ORDER BY timestamp ASC
    List<OpenAIMessage> findByRecipeIdOrderByTimestampAsc(Long recipeId);

    @Query(value = "SELECT * FROM messages " +
                   "WHERE recipe_id = :recipeId " +
                   "ORDER BY timestamp DESC " +
                   "LIMIT :limit", nativeQuery = true)
    List<OpenAIMessage> findLastMessages(@Param("recipeId") Long recipeId, @Param("limit") int limit);
}