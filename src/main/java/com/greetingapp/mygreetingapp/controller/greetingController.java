package com.greetingapp.mygreetingapp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class greetingController {

    @GetMapping
    public String getGreeting() {
        return "{\"message\": \"Hello, GET request received!\"}";
    }

    @PostMapping(value = "/post")
    public String postGreeting() {
        return "{\"message\": \"Hello, POST request received!\"}";
    }

    @PutMapping(value = "/put")
    public String putGreeting() {
        return "{\"message\": \"Hello, PUT request received!\"}";
    }

    @DeleteMapping(value = "/del")
    public String deleteGreeting() {
        return "{\"message\": \"Hello, DELETE request received!\"}";
    }
}