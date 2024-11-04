package com.ofg.hairdresser.model.response;

import com.ofg.hairdresser.security.token.Token;

public record AuthResponse(UserResponse userResponse, Token token) {
}
