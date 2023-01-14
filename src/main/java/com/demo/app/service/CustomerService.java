package com.demo.app.service;

import com.demo.app.dto.UpdateCustomerDto;

public interface CustomerService {

    UpdateCustomerDto updateCustomer(UpdateCustomerDto customerDto);

    boolean deleteCustomer();

}
