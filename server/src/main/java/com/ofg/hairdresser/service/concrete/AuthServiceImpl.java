package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.exception.authentication.AuthenticationException;
import com.ofg.hairdresser.exception.authentication.UserInactiveException;
import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.request.SignInCredentials;
import com.ofg.hairdresser.model.request.UserCreateRequest;
import com.ofg.hairdresser.model.response.AuthResponse;
import com.ofg.hairdresser.model.response.UserResponse;
import com.ofg.hairdresser.security.token.Token;
import com.ofg.hairdresser.security.token.TokenService;
import com.ofg.hairdresser.service.abstact.AuthService;
import com.ofg.hairdresser.service.abstact.UserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponse login(SignInCredentials signInCredentials) {
        User user = userService.getUserByEmail(signInCredentials.email());

        validateUser(user, signInCredentials.password());

        Token token = tokenService.createToken(user, signInCredentials);
        return new AuthResponse(new UserResponse(user), token);
    }

    @Override
    public UserResponse register(UserCreateRequest userCreateRequest) {
        return userService.addUser(userCreateRequest);
    }

    private void validateUser(User user, String rawPassword) {
        if (!user.isActive()) {
            throw new UserInactiveException();
        }
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new AuthenticationException();
        }
    }
}