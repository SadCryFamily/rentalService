package com.demo.app.service;

import com.demo.app.auth.pojo.JwtResponse;
import com.demo.app.dto.ActivateCustomerDto;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.LoginCustomerDto;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public interface AuthService {

    String registerCustomer(CreateCustomerDto customerDto) throws MessagingException, FileNotFoundException;

    JwtResponse loginCustomer(LoginCustomerDto customerDto);

    boolean activateCustomerAccount(ActivateCustomerDto customerDto);

}
