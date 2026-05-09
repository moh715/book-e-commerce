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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("📦 BookVerse — Order Confirmed");
        message.setText(
                "Thank you for your order!\n\n" +
                "Total charged: $" + String.format("%.2f", total) + "\n\n" +
                "We will ship your books shortly.\n\n" +
                "— The BookVerse Team"
        );
        mailSender.send(message);
    }
}
