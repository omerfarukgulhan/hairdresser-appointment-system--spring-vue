package com.ofg.barber.exception.email;

import com.ofg.barber.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class ActivationNotificationException extends RuntimeException {
    public ActivationNotificationException() {
        super(Messages.getMessageForLocale("app.msg.create.user.email.failure", LocaleContextHolder.getLocale()));
    }
}
