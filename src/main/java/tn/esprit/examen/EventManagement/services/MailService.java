package tn.esprit.examen.EventManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // Generic email sender
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // Event created notification
    public void sendEventCreatedNotification(String userEmail, String eventTitle) {
        String subject = "Your Event Submission is Received";
        String body = "Hello,\n\n" +
                "Thank you for submitting your event \"" + eventTitle + "\".\n" +
                "Our admins will review it and notify you once it's approved or rejected.\n\n" +
                "Best regards,\nEvent Management Team";
        sendEmail(userEmail, subject, body);
    }

    // Event approved notification
    public void sendEventApprovedNotification(String userEmail, String eventTitle) {
        String subject = "Your Event is Approved!";
        String body = "Hello,\n\n" +
                "Congratulations! Your event \"" + eventTitle + "\" has been approved by the admins.\n" +
                "It is now visible to all users.\n\n" +
                "Best regards,\nEvent Management Team";
        sendEmail(userEmail, subject, body);
    }

    // Event rejected notification
    public void sendEventRejectedNotification(String userEmail, String eventTitle, String reason) {
        String subject = "Your Event has been Rejected";
        String body = "Hello,\n\n" +
                "We regret to inform you that your event \"" + eventTitle + "\" was rejected.\n" +
                "Reason: " + reason + "\n\n" +
                "Please contact support for more information.\n\n" +
                "Best regards,\nEvent Management Team";
        sendEmail(userEmail, subject, body);
    }
}
