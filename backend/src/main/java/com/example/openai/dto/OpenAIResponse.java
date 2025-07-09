package com.example.openai.dto;

public class OpenAIResponse {
    private String aiMsg;
    private int status;

    public OpenAIResponse(String aiMsg, int status) {
        this.aiMsg = aiMsg;
        this.status = status;
    }

    // Getters are needed for JSON serialization
    public String getAiMsg() {
        return aiMsg;
    }

    public int getStatus() {
        return status;
    }
}