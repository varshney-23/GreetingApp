package com.greetingapp.mygreetingapp.service;


import com.greetingapp.mygreetingapp.dto.authUserDTO;
import com.greetingapp.mygreetingapp.dto.loginDTO;
import com.greetingapp.mygreetingapp.exception.userException;
import com.greetingapp.mygreetingapp.interfaces.IAuthService;
import com.greetingapp.mygreetingapp.repository.userRepository;
import com.greetingapp.mygreetingapp.util.EmailSenderService;
import com.greetingapp.mygreetingapp.util.jwttoken;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class authService implements IAuthService {
    @Autowired
    userRepository userRepo;

    @Autowired
    jwttoken tokenUtil;

    @Autowired
    EmailSenderService emailSenderService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(authUserDTO userDTO) throws Exception {
        try {
            User user = new User(userDTO);
            System.out.println(user);
            userRepo.save(user);

            String token = tokenUtil.createToken(user.getUserId());

            // Send email safely
            try {
                emailSenderService.sendEmail(
                        user.getEmail(),
                        "Registered in Greeting App",
                        "Hi " + user.getFirstName() + ",\n\nYou have been successfully registered!\n\nYour details:\n\n" +
                                "User Id: " + user.getUserId() + "\nFirst Name: " + user.getFirstName() + "\nLast Name: " + user.getLastName() +
                                "\nEmail: " + user.getEmail() + "\nToken: " + token
                );
            } catch (Exception emailException) {
                System.err.println("Error sending email: " + emailException.getMessage());
            }

            return user;
        } catch (Exception e) {
            throw new userException("Registration failed: " + e.getMessage());
        }
    }


    @Override
    public String login(loginDTO logDTO){
        Optional<com.greetingapp.mygreetingapp.model.User> user= Optional.ofNullable(userRepo.findByEmail(logDTO.getEmail()));
        if (user.isPresent() && user.get().getPassword().equals(logDTO.getPassword()) ){
            emailSenderService.sendEmail(user.get().getEmail(),"Logged in Successfully!", "Hii...."+user.get().getFirstName()+"\n\n You have successfully logged in into Greeting App!");
            return "Congratulations!! You have logged in successfully!";
        }else {
            throw new userException("Sorry! Email or Password is incorrect!");
        }
    }
}