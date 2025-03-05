package com.greetingapp.mygreetingapp.controller;


import com.greetingapp.mygreetingapp.dto.authUserDTO;
import com.greetingapp.mygreetingapp.dto.loginDTO;
import com.greetingapp.mygreetingapp.dto.responseDTO;
import com.greetingapp.mygreetingapp.service.authService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
//@RequestMapping("/auth")
public class authController {
    @Autowired
    authService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<responseDTO> register(@Valid @RequestBody authUserDTO userDTO) throws Exception{
        authUserDTO user = new authUserDTO();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());

        User savedUser = authenticationService.register(user);
        responseDTO responseUserDTO = new responseDTO("User details submitted!", savedUser);

        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<responseDTO> login(@Valid @RequestBody loginDTO loginDTO){
        String result=authenticationService.login(loginDTO);
        responseDTO responseUserDTO=new responseDTO("Login successfully!!",result);
        return  new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

}
