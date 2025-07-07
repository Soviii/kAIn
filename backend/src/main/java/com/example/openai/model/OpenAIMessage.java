package com.example.openai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "messages") // Maps this entity to the "messages" table in the database
public class OpenAIMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to SERIAL in PostgreSQL (auto-increment)
    private Long id;

    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId; // Foreign key to the recipes table (no JPA relationship here, just the ID)

    @Column(nullable = false, length = 20)
    private String sender; // Should be either 'user' or 'ai'

    @Column(name = "message_text", nullable = false, columnDefinition = "TEXT")
    private String messageText; // Uses PostgreSQL TEXT type for potentially long messages

    @Column(nullable = false)
    private LocalDateTime timestamp; // Maps to TIMESTAMP in PostgreSQL

    // Convenience constructor for easy instantiation
    public OpenAIMessage(Integer recipeId, String sender, String messageText) {
        this.recipeId = recipeId;
        this.sender = sender; // either 'user' or 'ai'
        this.messageText = messageText;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}