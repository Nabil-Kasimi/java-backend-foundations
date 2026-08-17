package com.example.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of(
                "message", "Hello from Spring Boot!",
                "time", LocalDateTime.now().toString());
    }

    @GetMapping("/api/status")
    public Map<String, String> status() {
        return Map.of(
                "status", "running",
                "thread", Thread.currentThread().toString());
    }
}
