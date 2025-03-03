package com.greetingapp.mygreetingapp.service;

import com.greetingapp.mygreetingapp.model.greetingEntity;
import com.greetingapp.mygreetingapp.repository.greetingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class greetingService {

    private static greetingRepository gsr;

    public greetingService(greetingRepository gsr) {
        this.gsr = gsr;
    }

    public static String getPersonalizedGreeting(String firstName, String lastName) {
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

    public Optional<greetingEntity> getGreetingById(Long id) {
        return gsr.findById(id);
    }
}
