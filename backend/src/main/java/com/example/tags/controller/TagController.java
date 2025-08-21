package com.example.tags.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.JwtConfig;
import com.example.tags.dto.AllTagsResponseDTO;
import com.example.tags.service.TagService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final JwtConfig jwtConfig;

    public TagController(TagService tagService, JwtConfig jwtConfig) {
        this.tagService = tagService;
        this.jwtConfig = jwtConfig;
    }

    // endpoint for retrieving all possible tags
    @GetMapping
    public ResponseEntity<AllTagsResponseDTO> getAllTags(@CookieValue(value = "kAIn-jwt", required = false) String token, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);

        return ResponseEntity.ok(this.tagService.getAllTags());
    }
}
