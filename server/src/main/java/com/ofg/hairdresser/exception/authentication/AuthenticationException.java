package com.ofg.hairdresser.exception.authentication;

import com.ofg.hairdresser.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super(Messages.getMessageForLocale("app.msg.auth.invalid.credentials", LocaleContextHolder.getLocale()));
    }
}
