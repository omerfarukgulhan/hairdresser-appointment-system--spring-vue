package com.ofg.hairdresser.service.concrete;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.ofg.hairdresser.service.abstact.SmsSenderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
    private final String accountSid;
    private final String authToken;
    private final String fromPhoneNumber;

    @Autowired
    public SmsSenderServiceImpl(@Value("${app.twilio.account.sid}") String accountSid,
                                @Value("${app.twilio.auth.token}") String authToken,
                                @Value("${app.twilio.phone.number}") String fromPhoneNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromPhoneNumber = fromPhoneNumber;
    }

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendSms(String toPhoneNumber, String messageBody) {
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();
    }
}
