package com.ofg.hairdresser.service.abstact;

public interface SmsSenderService {
    void sendSms(String toPhoneNumber, String messageBody);
}
