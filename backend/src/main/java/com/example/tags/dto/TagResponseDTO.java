package com.example.tags.dto;

import com.example.tags.model.Tag;

public class TagResponseDTO {
    private String name;

    public TagResponseDTO() {} // for JPA

    public TagResponseDTO(Tag tag) {
        this.name = tag.getName();
    }

    public TagResponseDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
