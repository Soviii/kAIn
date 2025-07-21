package com.example.openai.model;

public class ChatMessage {
    private String sender;
    private String msg;

    public ChatMessage(OpenAIMessage dbMsg) {
        this.sender = dbMsg.getSender();
        this.msg = dbMsg.getMessageText();
    }

    public String getSender() {
        return this.sender;
    }

    public String getMsg() {
        return this.msg;
    }
}
