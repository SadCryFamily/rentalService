package com.demo.app.controller;

import com.demo.app.dto.UpdateCustomerDto;
import com.demo.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize(value = "hasRole('USER')")
    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UpdateCustomerDto updateCustomer(@RequestBody @Valid UpdateCustomerDto customerDto) {
        return customerService.updateCustomer(customerDto);
    }

    @PreAuthorize(value = "hasRole('USER')")
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteCustomer() {
        return customerService.deleteCustomer();
    }

}
