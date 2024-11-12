package com.ofg.hairdresser.service.concrete;

import com.ofg.hairdresser.model.entity.Appointment;
import com.ofg.hairdresser.service.abstact.AppointmentService;
import com.ofg.hairdresser.service.abstact.EmailService;
import com.ofg.hairdresser.service.abstact.ReminderService;
import com.ofg.hairdresser.service.abstact.SmsSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {
    private final SmsSenderService smsSenderService;
    private final EmailService emailService;
    private final AppointmentService appointmentService;

    @Autowired
    public ReminderServiceImpl(SmsSenderService smsSenderService,
                               EmailService emailService,
                               AppointmentService appointmentService) {
        this.smsSenderService = smsSenderService;
        this.emailService = emailService;
        this.appointmentService = appointmentService;
    }

    @Scheduled(cron = "0 0,30 * * * *")
    @Override
    public void sendReminders() {
        List<Appointment> appointments = appointmentService.getUpcomingAppointmentsWithinMinutes(30);
        for (Appointment appointment : appointments) {
            String message = "You have a hairdresser appointment in 30 minutes.";
            smsSenderService.sendSms(appointment.getUser().getPhoneNumber(), message);

            String subject = "Appointment Reminder";
            emailService.sendEmail(appointment.getUser().getEmail(), message, subject);
        }
    }
}
