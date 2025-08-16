package com.example.openai.model;

public class ChatMessageDTO {
    private String sender;
    private String msg;

    public ChatMessageDTO(OpenAIMessage dbMsg) {
        this.sender = dbMsg.getSender();
        this.msg = dbMsg.getMessageText();
    }

    public String getSender() {
        return this.sender;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMsg(String msg) {
        this.msg = msg;   
    }
}
