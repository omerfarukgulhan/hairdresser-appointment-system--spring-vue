package com.ofg.barber.model.request;

import jakarta.validation.constraints.Email;

public record UserPasswordResetRequest(@Email String email) {
}