package com.example.tags.repository;

import com.example.tags.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface TagsRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    List<Tag> findAllByNameIn(List<String> tagNames);
}
