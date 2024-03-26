package org.example.infsistem;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class mailer {

    private static final String FROM_EMAIL = "assdasdasaasdasd";
    private static final String PASSWORD = "assdsdasadsda"; // Password for the FROM_EMAIL account

    public static void sendEmail(String to, String subject, String body) {
        // Postavke mail servera
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Set the username and password
        properties.put("mail.smtp.user", FROM_EMAIL);
        properties.put("mail.smtp.password", PASSWORD);

        // Sesija za slanje emaila
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            // Stvaranje poruke
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            // Slanje poruke
            Transport.send(message);
            System.out.println("Poruka uspješno poslana na: " + to);
        } catch (MessagingException e) {
            System.out.println("Došlo je do pogreške prilikom slanja poruke: " + e.getMessage());
        }
    }
}


