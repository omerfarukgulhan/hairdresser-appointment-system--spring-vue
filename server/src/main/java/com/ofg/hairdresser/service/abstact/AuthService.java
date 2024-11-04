package com.ofg.hairdresser.service.abstact;

import com.ofg.hairdresser.model.request.SignInCredentials;
import com.ofg.hairdresser.model.request.UserCreateRequest;
import com.ofg.hairdresser.model.response.AuthResponse;
import com.ofg.hairdresser.model.response.UserResponse;

public interface AuthService {
    AuthResponse login(SignInCredentials signInCredentials);

    UserResponse register(UserCreateRequest userCreateRequest);
}
