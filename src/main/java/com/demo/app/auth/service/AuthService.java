package com.demo.app.auth.service;

import com.demo.app.auth.pojo.JwtResponse;
import com.demo.app.dto.ActivateCustomerDto;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.LoginCustomerDto;

public interface AuthService {

    String registerCustomer(CreateCustomerDto customerDto);

    JwtResponse loginCustomer(LoginCustomerDto customerDto);

    boolean activateCustomerAccount(ActivateCustomerDto customerDto);

}
