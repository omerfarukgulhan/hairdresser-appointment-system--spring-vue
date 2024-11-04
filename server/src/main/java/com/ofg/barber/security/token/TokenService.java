package com.ofg.barber.security.token;

import com.ofg.barber.model.entity.User;
import com.ofg.barber.model.request.SignInCredentials;

import java.util.Optional;

public interface TokenService {
    Token createToken(User user, SignInCredentials signInCredentials);

    Optional<User> verifyToken(String authorizationHeader);
}