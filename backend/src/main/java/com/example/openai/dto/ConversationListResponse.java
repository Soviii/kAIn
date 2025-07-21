package com.example.openai.dto;

import com.example.openai.model.OpenAIMessage;
import com.example.openai.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;


public class ConversationListResponse {
    private List<ChatMessage> messages;

    public ConversationListResponse() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(OpenAIMessage entry) {
        messages.add(new ChatMessage(entry)); // makes a ChatMessage to only pass "sender" and "message"; reduces info necessary to frontend
    }

    public List<ChatMessage> getMessages() {
        return this.messages;
    }
}
