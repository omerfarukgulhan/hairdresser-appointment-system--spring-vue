package com.ofg.hairdresser.exception.email;

import com.ofg.hairdresser.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class ActivationNotificationException extends RuntimeException {
    public ActivationNotificationException() {
        super(Messages.getMessageForLocale("app.msg.create.user.email.failure", LocaleContextHolder.getLocale()));
    }
}
