package com.demo.app.service;

import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.entity.Customer;
import com.demo.app.mapper.CustomerMapper;
import com.demo.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CreateCustomerDto createCustomer(CreateCustomerDto customerDto) {
        Customer creatingCustomer = customerMapper.toCustomer(customerDto);
        customerRepository.save(creatingCustomer);

        return customerDto;
    }
}
