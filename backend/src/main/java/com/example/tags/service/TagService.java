package com.example.tags.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tags.dto.AllTagsResponseDTO;
import com.example.tags.model.Tag;
import com.example.tags.repository.TagsRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {
    private final TagsRepository tagRepository;

    public TagService(TagsRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // retrieves all tags from "tags" table
    @Transactional(readOnly = true)
    public AllTagsResponseDTO getAllTags() {
        List<Tag> tags = tagRepository.findAll();

        AllTagsResponseDTO allTagsResponseDTO = new AllTagsResponseDTO();
        allTagsResponseDTO.setTags(tags);

        return allTagsResponseDTO;
    }
}
