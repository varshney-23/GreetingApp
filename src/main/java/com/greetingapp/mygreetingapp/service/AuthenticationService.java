package com.greetingapp.mygreetingapp.service;

import com.greetingapp.mygreetingapp.dto.AuthUserDTO;
import com.greetingapp.mygreetingapp.dto.LoginDTO;
import com.greetingapp.mygreetingapp.exception.UserException;
import com.greetingapp.mygreetingapp.interfaces.IAuthenticationService;
import com.greetingapp.mygreetingapp.model.AuthUser;
import com.greetingapp.mygreetingapp.repository.AuthUserRepository;
import com.greetingapp.mygreetingapp.util.EmailSenderService;
import com.greetingapp.mygreetingapp.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
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
        log.info("Starting registration process for user: {}", userDTO.getEmail());

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

        log.info("User registered successfully: {}", user.getEmail());
        return user;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        log.info("Attempting login for user: {}", loginDTO.getEmail());
        Optional<AuthUser> user = Optional.ofNullable(authUserRepository.findByEmail(loginDTO.getEmail()));

        if (user.isPresent()) {
            if (passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                emailSenderService.sendEmail(user.get().getEmail(), "Logged in Successfully!", "Hi "
                        + user.get().getFirstName() + ",\n\nYou have successfully logged in to the Greeting App!");

                log.info("Login successful for user: {}", loginDTO.getEmail());
                return "Congratulations!! You have logged in successfully!";
            } else {
                log.warn("Incorrect password for user: {}", loginDTO.getEmail());
                throw new UserException("Sorry! Email or Password is incorrect!");
            }
        } else {
            log.warn("User not found for email: {}", loginDTO.getEmail());
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
    @Override
    public String forgotPassword(String email, String newPassword) {
        log.info("Processing forgot password request for user: {}", email);
        AuthUser user = authUserRepository.findByEmail(email);

        if (user == null) {
            log.error("User not found for email: {}", email);
            throw new UserException("Sorry! We cannot find the user email: " + email);
        }

        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        authUserRepository.save(user);

        emailSenderService.sendEmail(user.getEmail(),
                "Password Reset Successful",
                "Hi " + user.getFirstName() + ",\n\nYour password has been successfully changed!");

        log.info("Password changed successfully for user: {}", email);
        return "Password has been changed successfully!";
    }

    @Override
    public String resetPassword(String email, String currentPassword, String newPassword) {
        log.info("Processing reset password request for user: {}", email);
        AuthUser user = authUserRepository.findByEmail(email);

        if (user == null) {
            log.error("User not found for email: {}", email);
            throw new UserException("User not found with email: " + email);
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            log.warn("Incorrect current password for user: {}", email);
            throw new UserException("Current password is incorrect!");
        }

        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        authUserRepository.save(user);

        emailSenderService.sendEmail(user.getEmail(),
                "Password Reset Successful",
                "Hi " + user.getFirstName() + ",\n\nYour password has been successfully updated!");

        log.info("Password reset successfully for user: {}", email);
        return "Password reset successfully!";
    }
}
