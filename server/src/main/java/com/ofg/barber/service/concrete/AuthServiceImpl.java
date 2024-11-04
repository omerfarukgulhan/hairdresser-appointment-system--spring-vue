package com.ofg.barber.service.concrete;

import com.ofg.barber.exception.authentication.AuthenticationException;
import com.ofg.barber.exception.authentication.UserInactiveException;
import com.ofg.barber.model.entity.User;
import com.ofg.barber.model.request.SignInCredentials;
import com.ofg.barber.model.request.UserCreateRequest;
import com.ofg.barber.model.response.AuthResponse;
import com.ofg.barber.model.response.UserResponse;
import com.ofg.barber.security.token.Token;
import com.ofg.barber.security.token.TokenService;
import com.ofg.barber.service.abstact.AuthService;
import com.ofg.barber.service.abstact.UserService;
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