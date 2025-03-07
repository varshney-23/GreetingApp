package com.greetingapp.mygreetingapp.service;

import com.greetingapp.mygreetingapp.dto.AuthUserDTO;
import com.greetingapp.mygreetingapp.dto.LoginDTO;
import com.greetingapp.mygreetingapp.exception.UserException;
import com.greetingapp.mygreetingapp.interfaces.IAuthenticationService;
import com.greetingapp.mygreetingapp.model.AuthUser;
import com.greetingapp.mygreetingapp.repository.AuthUserRepository;
import com.greetingapp.mygreetingapp.util.EmailSenderService;
import com.greetingapp.mygreetingapp.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    JwtToken tokenUtil;

    @Autowired
    EmailSenderService emailSenderService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        AuthUser user = new AuthUser(userDTO);

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
    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> user = Optional.ofNullable(authUserRepository.findByEmail(loginDTO.getEmail()));

        if (user.isPresent()) {
            // Check if the password matches the encrypted password
            if (passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                emailSenderService.sendEmail(user.get().getEmail(), "Logged in Successfully!", "Hi "
                        + user.get().getFirstName() + ",\n\nYou have successfully logged in into Greeting App!");

                return "Congratulations!! You have logged in successfully!";
            } else {
                throw new UserException("Sorry! Email or Password is incorrect!");
            }
        } else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
    @Override
    public String forgotPassword(String email, String newPassword) {
        AuthUser user = authUserRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("Sorry! We cannot find the user email: " + email);
        }
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);

        authUserRepository.save(user);

        emailSenderService.sendEmail(user.getEmail(),
                "Password Reset Successful",
                "Hi " + user.getFirstName() + ",\n\nYour password has been successfully changed!");

        return "Password has been changed successfully!";
    }

    @Override
    public String resetPassword(String email, String currentPassword, String newPassword) {
        AuthUser user = authUserRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new UserException("Current password is incorrect!");
        }
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        authUserRepository.save(user);

        emailSenderService.sendEmail(user.getEmail(),
                "Password Reset Successful",
                "Hi " + user.getFirstName() + ",\n\nYour password has been successfully updated!");

        return "Password reset successfully!";
    }
}
