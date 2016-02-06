package com.kcbierco.mailer;

import javax.mail.MessagingException;
import java.util.List;

public interface EmailService {

    public void sendEmail(List<String> recipients, String subject, String message, String sourceEmail, String emailPassword) throws MessagingException;
}
