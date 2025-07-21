package com.example.openai.repository;

import com.example.openai.model.ChatSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<ChatSummary, Long> {
    List<ChatSummary> findByRecipeIdOrderByTimestampDesc(Integer recipeId);
}
