package com.demo.app.service;

import java.math.BigDecimal;

public interface ActivationService {

    void saveActivationCode(String username, BigDecimal activationCode);

    BigDecimal retrieveActivationCode(String username);

    boolean isActivationCodeValid(String username);

    void forceDeleteActivationCode(String username);

}
