package com.servicemanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SmtpEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    private MailSender mailSender;

    public SmtpEmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        new Thread(() -> {
            LOG.debug("O sistema está enviando o email...");
            mailSender.send(simpleMailMessage);
            LOG.debug("Email enviado!");
        }).start();
    }
}
