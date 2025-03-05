package com.greetingapp.mygreetingapp.interfaces;

import com.greetingapp.mygreetingapp.dto.authUserDTO;
import com.greetingapp.mygreetingapp.dto.loginDTO;
import org.springframework.security.core.userdetails.User;

public interface IAuthService {
    User register(authUserDTO userDTO) throws Exception;

    String login(loginDTO loginDTO);
}
