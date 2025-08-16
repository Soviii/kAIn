package com.example.openai.model;

public class ChatSummaryDTO {
    private String text;

    public ChatSummaryDTO(){} // in case JPA needs one

    public ChatSummaryDTO(ChatSummary s) {
        this.text = s.getSummaryText();
    }

    public String getSummary() {
        return this.text;
    }

    public void setSummary(String s) {
        this.text = s;
    }
}
