package com.demo.app.service;

import com.demo.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public BigDecimal sendActivationEmail(String sendTo) {

        SimpleMailMessage msg = new SimpleMailMessage();

        BigDecimal activationCode = BigDecimal.valueOf(Math.random() * 1000000)
                .setScale(0, RoundingMode.HALF_DOWN);

        msg.setTo(sendTo);
        msg.setSubject("Rental Service registration");
        msg.setText("Hello! Here is your activation code: " + "R-" + activationCode);

        javaMailSender.send(msg);

        return activationCode;
    }
}
