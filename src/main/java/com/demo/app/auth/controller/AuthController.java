package com.demo.app.auth.controller;

import com.demo.app.auth.service.AuthService;
import com.demo.app.dto.CreateCustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerCustomer(@RequestBody @Valid CreateCustomerDto customerDto) {
        return authService.registerCustomer(customerDto);
    }

}
