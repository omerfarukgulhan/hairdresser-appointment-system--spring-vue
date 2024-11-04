package com.ofg.barber.exception.authentication;

import com.ofg.barber.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(Messages.getMessageForLocale("app.msg.activate.user.invalid.password", LocaleContextHolder.getLocale()));
    }
}