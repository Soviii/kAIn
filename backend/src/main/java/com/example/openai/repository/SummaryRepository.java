package com.example.openai.repository;

import com.example.openai.model.ChatSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
// import java.util.Optional;

@Repository
public interface SummaryRepository extends JpaRepository<ChatSummary, Long> {
    @Query(value = "SELECT * FROM summaries " +
                "WHERE recipe_id = :recipeId " +
                "ORDER BY created_at DESC " +
                "LIMIT :limit", nativeQuery = true)
    List<ChatSummary> findLastSummaries(@Param("recipeId") Long recipeId, @Param("limit") int limit); // TODO: determine if using multiple summaries or one for prompt engineering...
    // List<ChatSummary> findLastSummaries(@Param("recipeId") Long recipeId, @Param("limit") int limit);
}
