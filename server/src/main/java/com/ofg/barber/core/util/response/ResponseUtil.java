package com.ofg.barber.core.util.response;

import com.ofg.barber.core.util.message.Messages;
import com.ofg.barber.core.util.results.ApiDataResponse;
import com.ofg.barber.core.util.results.ApiResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ApiDataResponse<T>> createApiDataResponse(T data, String messageKey, HttpStatus status) {
        String message = Messages.getMessageForLocale(messageKey, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ApiDataResponse<>(true, message, data), status);
    }

    public static ResponseEntity<ApiResponse> createApiResponse(String messageKey, HttpStatus status) {
        String message = Messages.getMessageForLocale(messageKey, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ApiResponse(true, message), status);
    }
}
