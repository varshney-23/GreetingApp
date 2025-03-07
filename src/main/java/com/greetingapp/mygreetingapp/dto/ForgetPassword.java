package com.greetingapp.mygreetingapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class ForgetPassword {
    @NotBlank(message = "Password cannot be empty")
    private String password;
}