package com.demo.app.service;

import java.math.BigDecimal;

public interface EmailService {

    BigDecimal sendActivationEmail(String sendTo);

}
