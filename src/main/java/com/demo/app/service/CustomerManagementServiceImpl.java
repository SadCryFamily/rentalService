package com.demo.app.service;

import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.entity.Customer;
import com.demo.app.exception.CreateExistingCustomerException;
import com.demo.app.exception.NullCustomerException;
import com.demo.app.mapper.CustomerMapper;
import com.demo.app.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class CustomerManagementServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CreateCustomerDto createCustomer(CreateCustomerDto customerDto) {

        Optional<CreateCustomerDto> customerDtoOptional = Optional.ofNullable(customerDto);

        if (customerDtoOptional.isPresent()) {

            String checkedEmail = customerDtoOptional.get().getCustomerEmail();

            if (!customerRepository.existsByCustomerEmail(checkedEmail)) {

                CreateCustomerDto createCustomerDto
                        = customerDtoOptional.get();

                Customer savedCustomer =
                        customerRepository.save(customerMapper.toCustomer(createCustomerDto));

                log.info("CREATED Customer by ID: {}, AT TIME: {}",
                        savedCustomer.getCustomerId(), savedCustomer.getCreatedAt());

                return createCustomerDto;

            } else {
                log.error("ERROR CREATE Customer AT TIME: {}", new Date());
                throw new CreateExistingCustomerException("Customer with given email already exists");
            }

        } else {
            throw new NullCustomerException("Cant create a null customer");
        }

    }

}
