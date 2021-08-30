package com.servicemanagement.service;

import com.servicemanagement.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.*;

class SmtpEmailServiceTest {

    @Mock
    private MailSender mailSender;

    private SmtpEmailService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.service =  spy(new SmtpEmailService(mailSender));
    }

    @Test
    public void sendEmailTest() {
        User user = new User("Test","test@gmail.com","test");
        service.sendEmail(user, "Email test", "testando");
        verify(service, atLeastOnce()).sendEmail(any(SimpleMailMessage.class));
    }

}