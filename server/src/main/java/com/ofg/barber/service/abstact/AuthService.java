package com.ofg.barber.service.abstact;

import com.ofg.barber.model.request.SignInCredentials;
import com.ofg.barber.model.request.UserCreateRequest;
import com.ofg.barber.model.response.AuthResponse;
import com.ofg.barber.model.response.UserResponse;

public interface AuthService {
    AuthResponse login(SignInCredentials signInCredentials);

    UserResponse register(UserCreateRequest userCreateRequest);
}
