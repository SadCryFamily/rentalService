package com.demo.app.controller;

import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public CreateCustomerDto createCustomer(@RequestBody CreateCustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }
}
