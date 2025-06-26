package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/api/goodbye")
    public Map<String, String> sayGoodbye() {
        return Map.of("message", "yu ");
    }
}

