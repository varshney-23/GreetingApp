package com.greetingapp.mygreetingapp.controller;

import com.greetingapp.mygreetingapp.model.greetingEntity;
import com.greetingapp.mygreetingapp.service.greetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/greeting")
public class greetingController {

    private final greetingService gsr;

    public greetingController(greetingService gsr){
        this.gsr = gsr;
    }

    @GetMapping("/personalized")
    public String getPersonalizedGreeting(@RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName) {
        return "{\"message\": \"" + greetingService.getPersonalizedGreeting(firstName, lastName) + "\"}";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGreetingById(@PathVariable Long id) {
        Optional<greetingEntity> greeting = gsr.getGreetingById(id);
        return greeting.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
