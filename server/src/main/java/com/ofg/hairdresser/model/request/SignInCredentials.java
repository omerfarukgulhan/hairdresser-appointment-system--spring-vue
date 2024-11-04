package com.ofg.hairdresser.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInCredentials(@Email String email, @NotBlank String password) {

}