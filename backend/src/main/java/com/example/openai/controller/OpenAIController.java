// controller/OpenAIController.java
package com.example.openai.controller;

import com.example.openai.dto.OpenAIRequest;
import com.example.openai.service.OpenAIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody OpenAIRequest request) {
        return this.openAIService.chat(request.message());
    }
}
