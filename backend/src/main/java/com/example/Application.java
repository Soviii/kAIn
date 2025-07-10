package com.example;  // <- base package for your entire app

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // <- enables auto-configuration + component scanning
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
