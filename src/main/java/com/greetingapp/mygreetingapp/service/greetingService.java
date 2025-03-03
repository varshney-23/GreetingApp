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

    public String getPersonalizedGreeting(String firstName, String lastName) {
        String message;
        if (firstName != null && lastName != null) {
            message = "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            message = "Hello, " + firstName + "!";
        } else if (lastName != null) {
            message = "Hello, " + lastName + "!";
        } else {
            message = "Hello World!";
        }

        greetingEntity greeting = new greetingEntity(message);
        gsr.save(greeting);

        return message;
    }

    public greetingEntity saveGreeting(String message) {
        greetingEntity greeting = new greetingEntity(message);
        return gsr.save(greeting);
    }

    public Optional<greetingEntity> getGreetingById(Long id) {
        return gsr.findById(id);
    }

    public List<greetingEntity> getAllGreetings() {
        return gsr.findAll();
    }
}
