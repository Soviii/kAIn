package com.example.openai.repository;

import com.example.openai.model.OpenAIMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenAIMessageRepository extends JpaRepository<OpenAIMessage, Long> {}
