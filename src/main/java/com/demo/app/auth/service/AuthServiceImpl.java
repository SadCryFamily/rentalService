package com.demo.app.auth.service;

import com.demo.app.auth.entity.CustomerRoles;
import com.demo.app.auth.entity.Role;
import com.demo.app.auth.repository.RoleRepository;
import com.demo.app.config.jwt.JwtUtils;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.entity.Customer;
import com.demo.app.exception.CreateExistingCustomerException;
import com.demo.app.exception.NullCustomerException;
import com.demo.app.mapper.CustomerMapper;
import com.demo.app.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public String registerCustomer(CreateCustomerDto customerDto) {

        Optional<CreateCustomerDto> optionalCustomerDto = Optional.ofNullable(customerDto);

        if (optionalCustomerDto.isEmpty()) throw new NullCustomerException("Customer cant be null");

        String email = customerDto.getCustomerEmail();
        String username = customerDto.getCustomerUsername();

        if (!customerRepository.existsByCustomerEmailOrCustomerUsername(email, username)) {

            Customer customer = customerMapper.toCustomer(customerDto);

            String encryptedPassword = passwordEncoder.encode(customerDto.getCustomerPassword());
            customer.setCustomerPassword(encryptedPassword);

            Role defaultRole = roleRepository.findByRoleName(CustomerRoles.ROLE_USER);

            customer.setRoles(Set.of(defaultRole));

            Customer logCustomer = customerRepository.save(customer);

            log.info("CREATED Customer by USERNAME: {}, AT TIME: {}",
                    logCustomer.getCustomerUsername(), logCustomer.getCreatedAt());

            return "Customer successfully created!";

        } else {

            log.error("ERROR CREATING Customer by USERNAME : {}, AT TIME: {}", username, new Date());

            throw new CreateExistingCustomerException("Customer already exists by given email or username");
        }

    }
}
