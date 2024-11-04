package com.ofg.barber.exception.general;

import com.ofg.barber.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super(Messages.getMessageForLocale("app.msg.data.id.not.found", LocaleContextHolder.getLocale(), id));
    }

    public NotFoundException(String data) {
        super(Messages.getMessageForLocale("app.msg.data.not.found", LocaleContextHolder.getLocale(), data));
    }
}