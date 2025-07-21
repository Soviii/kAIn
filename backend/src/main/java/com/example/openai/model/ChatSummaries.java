package com.example.openai.model;

import java.util.ArrayList;
import java.util.List;

public class ChatSummaries {
    private List<String> summaries;

    public ChatSummaries() {
        this.summaries = new ArrayList<>();
    }

    public void addSummary(String summary) {
        summaries.add(summary);
    }

    public List<String> getSummaries() {
        return this.summaries;
    }
}
