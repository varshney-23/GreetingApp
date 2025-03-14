package com.greetingapp.mygreetingapp.interfaces;

import com.greetingapp.mygreetingapp.model.Greeting;

import java.util.List;
import java.util.Optional;

public interface IGreetingService {

    String getSimpleGreeting();  // Added

    Greeting addGreeting(String message);

    Greeting saveGreeting(String firstName, String lastName);  // Added

    List<Greeting> getAllGreetings();

    Optional<Greeting> getGreetingById(Long id);

    Greeting updateGreeting(Long id, String newMessage);

    void deleteGreeting(Long id);
}