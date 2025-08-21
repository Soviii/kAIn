package com.example.tags.dto;
import java.util.ArrayList;
import java.util.List;

import com.example.tags.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllTagsResponseDTO {
    private List<TagResponseDTO> tags;

    public void setTags(List<Tag> dbTags) {
        tags = new ArrayList<>();

        for (Tag tag : dbTags) {
            tags.add(new TagResponseDTO(tag));
        }
    }
}
