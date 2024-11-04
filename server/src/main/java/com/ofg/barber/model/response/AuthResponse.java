package com.ofg.barber.model.response;

import com.ofg.barber.security.token.Token;

public record AuthResponse(UserResponse userResponse, Token token) {
}
