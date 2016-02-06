package com.kcbierco.fixture;

import com.kcbierco.mailer.EmailService;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by Richard on 2/6/16.
 */
public class MockEmailService implements EmailService {
    protected static int emailsSent = 0;

    @Override
    public void sendEmail(List<String> recipients, String subject, String message, String sourceEmail, String emailPassword) throws MessagingException {
        ++emailsSent;
    }

    public static int getNumberOfEmailsSent(){
        return emailsSent;
    }

    public static void clearCounter(){
        emailsSent = 0;
    }

}
