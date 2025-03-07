package com.greetingapp.mygreetingapp.interfaces;

import com.greetingapp.mygreetingapp.dto.AuthUserDTO;
import com.greetingapp.mygreetingapp.dto.LoginDTO;
import com.greetingapp.mygreetingapp.model.AuthUser;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;

    String login(LoginDTO loginDTO);

    String forgotPassword(String email, String newPassword);

}
