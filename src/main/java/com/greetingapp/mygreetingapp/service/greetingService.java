package com.greetingapp.mygreetingapp.service;

import org.springframework.stereotype.Service;

@Service
public class greetingService {
    public static String getGreetingMessage() {
        return "Hello World";
    }
}