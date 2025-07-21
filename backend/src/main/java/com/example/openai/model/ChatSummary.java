package com.example.openai.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "summaries")
public class ChatSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to SERIAL in PostgreSQL (auto-increment)
    private Long id; // PK; 

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId; // Foreign key to recipes table

    @Column(name = "summary_text", nullable = false)
    private String summaryText;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime timestamp; // Maps to TIMESTAMP in PostgreSQL

    // Required by JPA - must be at least protected
    protected ChatSummary() {
        // JPA uses this constructor
    }

    // Constructor for ease of initialization
    public ChatSummary(Integer recipeId, String summaryText) {
        this.recipeId = recipeId;
        this.summaryText = summaryText;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return this.id;
    }

    public Integer getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSummaryText() {
        return this.summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }
}
