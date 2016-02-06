package com.kcbierco.mailer.impl;

import com.kcbierco.mailer.EmailService;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * Created by Richard on 1/31/16.
 */
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(List<String> recipients, String subject, String message, String sourceEmail, String emailPassword) throws MessagingException {
        Transport transport = null;
        try {
            Properties mailProps = System.getProperties();
            mailProps.put("mail.smtp.port", "587");
            mailProps.put("mail.smtp.auth", "true");
            mailProps.put("mail.smtp.starttls.enable", "true");

            Session mailSession = Session.getDefaultInstance(mailProps, null);

            MimeMessage mailMessage = new MimeMessage(mailSession);
            recipients.forEach(address -> {
                try {
                    mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

            mailMessage.setSubject(subject);
            String emailBody = message;
            mailMessage.setContent(emailBody, "text/html");
            transport = mailSession.getTransport("smtp");

            transport.connect("smtp.gmail.com", sourceEmail, emailPassword);
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());

        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            transport.close();
        }

    }
}
