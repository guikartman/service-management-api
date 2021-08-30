package com.servicemanagement.service;

import com.servicemanagement.domain.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    /**
     * This method should prepare the email to be sent.
     *
     * @param user
     * @param subject
     * @param body
     */
    void sendEmail(User user, String subject, String body);

    /**
     * Method to send the email message.
     *
     * @param simpleMailMessage
     */
    void sendEmail(SimpleMailMessage simpleMailMessage);
}
