package com.example.openai.dto;

import com.example.openai.model.OpenAIMessage;
import com.example.openai.model.ChatMessageDTO;

import java.util.ArrayList;
import java.util.List;


public class ConversationListResponse {
    private List<ChatMessageDTO> messages;

    public ConversationListResponse() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(OpenAIMessage entry) {
        messages.add(new ChatMessageDTO(entry)); // makes a ChatMessage to only pass "sender" and "message"; reduces info necessary to frontend
    }

    public List<ChatMessageDTO> getMessages() {
        return this.messages;
    }
}
