package com.greetingapp.mygreetingapp.service;

import com.greetingapp.mygreetingapp.model.greetingEntity;
import com.greetingapp.mygreetingapp.repository.greetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class greetingService {

    private final greetingRepository gsr;

    @Autowired
    public greetingService(greetingRepository gsr) {
        this.gsr = gsr;
    }

    public greetingEntity saveGreeting(String message) {
        greetingEntity greeting = new greetingEntity();
        greeting.setMessage(message);
        return gsr.save(greeting);
    }

    public Optional<greetingEntity> getGreetingById(Long id) {
        return gsr.findById(id);
    }

    public List<greetingEntity> getAllGreetings() {
        return gsr.findAll();
    }

    public String getPersonalizedGreeting(String firstName, String lastName) {
        if (firstName == null && lastName == null) {
            return "Hello, Guest!";
        }
        return "Hello, " + (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "") + "!";
    }

    public greetingEntity updateGreeting(Long id, String newMessage) {
        return gsr.findById(id)
                .map(greeting -> {
                    greeting.setMessage(newMessage);
                    return gsr.save(greeting);
                })
                .orElseThrow(() -> new RuntimeException("Greeting not found with ID: " + id));
    }
}
