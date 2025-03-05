package com.greetingapp.mygreetingapp.service;

import com.greetingapp.mygreetingapp.dto.authUserDTO;
import com.greetingapp.mygreetingapp.dto.loginDTO;
import com.greetingapp.mygreetingapp.exception.userException;
import com.greetingapp.mygreetingapp.interfaces.IAuthService;
import com.greetingapp.mygreetingapp.repository.userRepository;
import com.greetingapp.mygreetingapp.util.EmailSenderService;
import com.greetingapp.mygreetingapp.util.jwttoken;


import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class authService implements IAuthService{

    @Autowired
    userRepository authUserRepository;

    @Autowired
    jwttoken tokenUtil;

    @Autowired
    EmailSenderService emailSenderService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(authUserDTO userDTO) throws Exception {
        User user = new authUser(userDTO);

        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);

        String token = tokenUtil.createToken(user.getUserId());
        authUserRepository.save(user);

        emailSenderService.sendEmail(user.getEmail(), "Registered in Greeting App", "Hi "
                + user.getFirstName() + ",\nYou have been successfully registered!\n\nYour registered details are:\n\n User Id:  "
                + user.getUserId() + "\n First Name:  "
                + user.getFirstName() + "\n Last Name:  "
                + user.getLastName() + "\n Email:  "
                + user.getEmail() + "\n Token:  " + token);

        return user;
    }



    @Override
    public String login(loginDTO logDTO) {
        Optional<User> user = Optional.ofNullable(authUserRepository.findByEmail(logDTO.getEmail()));

        if (user.isPresent()) {
            if (passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                emailSenderService.sendEmail(user.get().getEmail(), "Logged in Successfully!", "Hi "
                        + user.get().getFirstName() + ",\n\nYou have successfully logged in into Greeting App!");

                return "Congratulations!! You have logged in successfully!";
            } else {
                throw new userException("Sorry! Email or Password is incorrect!");
            }
        } else {
            throw new userException("Sorry! Email or Password is incorrect!");
        }
    }
}