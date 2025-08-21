package com.example.tags.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.tags.model.Tag;
import com.example.tags.repository.TagsRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagInitializer implements CommandLineRunner {

    private final TagsRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] tags = {"Dessert", "Breakfast", "Lunch", "Dinner", "Keto", "Gluten-Free", "Snack", "Drink", "Spicy", "Vegan", "Lactosse-free"};
        for (String name : tags) {
            tagRepository.findByName(name)
                .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build()));
        }
    }
}
