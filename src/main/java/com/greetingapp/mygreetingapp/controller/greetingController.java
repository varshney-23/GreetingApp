package com.greetingapp.mygreetingapp.controller;

import com.greetingapp.mygreetingapp.model.greetingEntity;
import com.greetingapp.mygreetingapp.service.greetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/greetings")
public class greetingController {

    private final greetingService gs;

    @Autowired
    public greetingController(greetingService gs) {
        this.gs = gs;
    }

    @PostMapping("/save")
    public greetingEntity saveGreeting(@RequestParam String message) {
        return gs.saveGreeting(message);
    }

    @GetMapping("/{id}")
    public Optional<greetingEntity> getGreetingById(@PathVariable Long id) {
        return gs.getGreetingById(id);
    }

    @GetMapping("/all")
    public List<greetingEntity> getAllGreetings() {
        return gs.getAllGreetings();
    }

    @GetMapping("/personalized")
    public String getPersonalizedGreeting(@RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName) {
        return gs.getPersonalizedGreeting(firstName, lastName);
    }

    @PutMapping("/{id}")
    public greetingEntity updateGreeting(@PathVariable Long id, @RequestParam String newMessage) {
        return gs.updateGreeting(id, newMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGreeting(@PathVariable Long id) {
        gs.deleteGreeting(id);
        return ResponseEntity.ok("Greeting deleted successfully!");
    }
}
