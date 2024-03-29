package com.demo.app.auth.controller;

import com.demo.app.auth.pojo.JwtResponse;
import com.demo.app.dto.ResendActivationDto;
import com.demo.app.service.AuthService;
import com.demo.app.dto.ActivateCustomerDto;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.LoginCustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.FileNotFoundException;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerCustomer(@RequestBody @Valid CreateCustomerDto customerDto) throws MessagingException, FileNotFoundException {
        return authService.registerCustomer(customerDto);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public JwtResponse loginCustomer(@RequestBody @Valid LoginCustomerDto customerDto) {
        return authService.loginCustomer(customerDto);
    }

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean activateCustomerAccount(@RequestBody @Valid ActivateCustomerDto customerDto) {
        return authService.activateCustomerAccount(customerDto);
    }

    @PostMapping("/resend-activation-code")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean restoreActivationCode(@RequestBody @Valid ResendActivationDto activationDto) throws MessagingException, FileNotFoundException {
        return authService.restoreActivationCode(activationDto);
    }

}
