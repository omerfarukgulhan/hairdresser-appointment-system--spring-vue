package com.ofg.hairdresser.exception.authentication;

import com.ofg.hairdresser.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class MissingAuthorizationHeaderException extends RuntimeException {
    public MissingAuthorizationHeaderException() {
        super(Messages.getMessageForLocale("app.msg.auth.header.missing", LocaleContextHolder.getLocale()));
    }
}