package com.ofg.barber.security.token;

import java.util.Base64;
import java.util.Optional;

import com.ofg.barber.model.entity.User;
import com.ofg.barber.model.request.SignInCredentials;
import com.ofg.barber.service.abstact.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.token-type", havingValue = "basic")
public class BasicTokenService implements TokenService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BasicTokenService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Token createToken(User user, SignInCredentials signInCredentials) {
        String emailColonPassword = signInCredentials.email() + ":" + signInCredentials.password();
        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());
        return new Token("Basic", token);
    }

    @Override
    public Optional<User> verifyToken(String authorizationHeader) {
        String base64Encoded = extractToken(authorizationHeader);
        if (base64Encoded == null) return Optional.empty();

        var decoded = new String(Base64.getDecoder().decode(base64Encoded));
        var credentials = decoded.split(":");
        if (credentials.length != 2) return Optional.empty();

        var email = credentials[0];
        var password = credentials[1];
        User user = userService.getUserByEmail(email);
        if (user == null) return Optional.empty();
        if (!passwordEncoder.matches(password, user.getPassword())) return Optional.empty();
        return Optional.of(user);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            return authorizationHeader.substring(6); // Remove "Basic " prefix
        }
        return null;
    }
}