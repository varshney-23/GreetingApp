package com.greetingapp.mygreetingapp.controller;

import com.greetingapp.mygreetingapp.dto.AuthUserDTO;
import com.greetingapp.mygreetingapp.dto.ForgetPassword;
import com.greetingapp.mygreetingapp.dto.LoginDTO;
import com.greetingapp.mygreetingapp.dto.ResponseDTO;
import com.greetingapp.mygreetingapp.model.AuthUser;
import com.greetingapp.mygreetingapp.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) throws Exception{
        AuthUser user=authenticationService.register(userDTO);
        ResponseDTO responseUserDTO =new ResponseDTO("User details is submitted!",user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        String result=authenticationService.login(loginDTO);
        ResponseDTO responseUserDTO=new ResponseDTO("Login successfully!!",result);
        return  new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

    @PutMapping("/auth/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email,
                                                      @Valid @RequestBody ForgetPassword forgotPasswordDTO) {
        String responseMessage = authenticationService.forgotPassword(email, forgotPasswordDTO.getPassword());
        ResponseDTO responseDTO = new ResponseDTO(responseMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}