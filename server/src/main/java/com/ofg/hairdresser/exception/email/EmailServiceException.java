package com.ofg.hairdresser.exception.email;

import com.ofg.hairdresser.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class EmailServiceException extends RuntimeException {
    public EmailServiceException() {
        super(Messages.getMessageForLocale("app.msg.email.service.error", LocaleContextHolder.getLocale()));
    }
}
