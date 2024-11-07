package com.ofg.hairdresser.exception;

import com.ofg.hairdresser.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AppointmentUnavailableException extends RuntimeException {
    public AppointmentUnavailableException() {
        super(Messages.getMessageForLocale("app.msg.appointment.unavailable", LocaleContextHolder.getLocale()));
    }

    public AppointmentUnavailableException(String message) {
        super(message);
    }
}
