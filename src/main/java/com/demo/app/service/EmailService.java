package com.demo.app.service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

public interface EmailService {

    BigDecimal sendActivationEmail(String sendTo) throws MessagingException, FileNotFoundException;

}
