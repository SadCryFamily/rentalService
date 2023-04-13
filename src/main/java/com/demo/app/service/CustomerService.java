package com.demo.app.service;

import com.demo.app.dto.RetrieveCustomerDto;
import com.demo.app.dto.UpdateCustomerDto;

public interface CustomerService {

    RetrieveCustomerDto retrieveCustomer();

    UpdateCustomerDto updateCustomer(UpdateCustomerDto customerDto);

    boolean deleteCustomer();

}
