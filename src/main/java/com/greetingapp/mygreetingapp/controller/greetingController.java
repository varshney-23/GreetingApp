package com.greetingapp.mygreetingapp.controller;

import com.greetingapp.mygreetingapp.service.greetingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class greetingController {

    private final greetingService gsr;

    public greetingController(greetingService gsr){
        this.gsr = gsr;
    }


    @GetMapping
    public String getGreeting() {
        return "{\"message\": \"" + greetingService.getGreetingMessage() + "\"}";
    }
}