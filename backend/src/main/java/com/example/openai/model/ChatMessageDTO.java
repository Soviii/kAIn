package com.example.openai.model;
import java.io.Serializable;

public class ChatMessageDTO implements Serializable {
    private static final Long serialVersionUID = 1L; // used for caching
    private String sender;
    private String msg;

    public ChatMessageDTO(){}

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
