package com.ofg.hairdresser.security.token;

import com.ofg.hairdresser.model.entity.User;
import com.ofg.hairdresser.model.request.SignInCredentials;

import java.util.Optional;

public interface TokenService {
    Token createToken(User user, SignInCredentials signInCredentials);

    Optional<User> verifyToken(String authorizationHeader);
}