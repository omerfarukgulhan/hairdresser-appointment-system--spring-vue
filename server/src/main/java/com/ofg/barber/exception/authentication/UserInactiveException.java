package com.ofg.barber.exception.authentication;

import com.ofg.barber.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserInactiveException extends RuntimeException {
    public UserInactiveException() {
        super(Messages.getMessageForLocale("app.msg.user.inactive", LocaleContextHolder.getLocale()));
    }
}