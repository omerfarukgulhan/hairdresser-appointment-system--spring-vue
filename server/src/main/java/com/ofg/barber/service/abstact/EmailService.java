package com.ofg.barber.service.abstact;

public interface EmailService {
    void sendActivationEmail(String email, String activationToken);
    void sendPasswordResetEmail(String email, String passwordResetToken);
}