package com.example.openai.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * A single chat exchange we store in Postgres.
 */
@Entity                      // Tells JPA this class maps to a table
@Table(name = "openai_messages")   // Optional: explicit table name
public class OpenAIMessage {

    // ───────────────────────────────────────────────────────────────
    // Columns
    // ───────────────────────────────────────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // auto‑increment PK
    private Long id;

    @Column(nullable = false, length = 4_096)             // user’s prompt
    private String userMessage;

    @Column(nullable = false, length = 4_096)             // model’s reply
    private String aiResponse;

    @Column(nullable = false, updatable = false)          // when stored
    private Instant createdAt = Instant.now();

    // ───────────────────────────────────────────────────────────────
    // Constructors
    // ───────────────────────────────────────────────────────────────
    protected OpenAIMessage() {}      // JPA needs a no‑arg ctor

    public OpenAIMessage(String userMessage, String aiResponse) {
        this.userMessage = userMessage;
        this.aiResponse  = aiResponse;
        System.out.println("hellow");
    }

    // ───────────────────────────────────────────────────────────────
    // Getters & setters  (generate in IDE or swap in Lombok @Data)
    // ───────────────────────────────────────────────────────────────
    public Long   getId()          { return id; }
    public String getUserMessage() { return userMessage; }
    public String getAiResponse()  { return aiResponse; }
    public Instant getCreatedAt()  { return createdAt; }

    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
    public void setAiResponse(String aiResponse)   { this.aiResponse  = aiResponse; }
}
