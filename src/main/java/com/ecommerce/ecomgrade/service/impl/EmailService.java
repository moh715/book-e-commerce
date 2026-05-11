package com.ecommerce.ecomgrade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail, Double total) {
        // We wrap the email in try-catch so that if the email server
        // is not configured, the order still goes through successfully.
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("📦 Maktabtna — Order Confirmed");
            message.setText(
                    "Thank you for your order!\n\n" +
                            "Total charged: $" + String.format("%.2f", total) + "\n\n" +
                            "We will ship your books shortly.\n\n" +
                            "— The Maktabtna Team"
            );
            mailSender.send(message);
        } catch (Exception e) {
            // Log the error but don't crash the application.
            // In a real project you'd use a logger: log.warn("Email failed: {}", e.getMessage())
            System.err.println("Warning: Could not send confirmation email to " + toEmail + ". Reason: " + e.getMessage());
        }
    }
}
