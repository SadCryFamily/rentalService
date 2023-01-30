package com.demo.app.service;

import com.demo.app.auth.entity.CustomerRoles;
import com.demo.app.auth.entity.Role;
import com.demo.app.auth.jwt.JwtUtils;
import com.demo.app.auth.pojo.JwtResponse;
import com.demo.app.auth.repository.RoleRepository;
import com.demo.app.dto.ActivateCustomerDto;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.LoginCustomerDto;
import com.demo.app.entity.Customer;
import com.demo.app.enums.ExceptionMessage;
import com.demo.app.exception.*;
import com.demo.app.mapper.CustomerMapper;
import com.demo.app.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public String registerCustomer(CreateCustomerDto customerDto) throws MessagingException, FileNotFoundException {

        Optional<CreateCustomerDto> optionalCustomerDto = Optional.ofNullable(customerDto);

        if (optionalCustomerDto.isEmpty()) {
            throw new NullCustomerException(ExceptionMessage.NULL_CUSTOMER_CREATION.getExceptionMessage());
        }

        String email = customerDto.getCustomerEmail();
        String username = customerDto.getCustomerUsername();

        if (!customerRepository.existsByCustomerEmailOrCustomerUsername(email, username)) {

            Customer customer = customerMapper.toCustomer(customerDto);

            String encryptedPassword = passwordEncoder.encode(customerDto.getCustomerPassword());
            customer.setCustomerPassword(encryptedPassword);

            Role defaultRole = roleRepository.findByRoleName(CustomerRoles.ROLE_USER);

            customer.setRoles(Set.of(defaultRole));

            BigDecimal activationCode =
                    emailService.sendActivationEmail(customer.getCustomerEmail());
            customer.setActivationCode(activationCode);

            Customer logCustomer = customerRepository.save(customer);

            log.info("CREATED Customer by USERNAME: {}, AT TIME: {}",
                    username, new Date());

            return "Customer successfully created!";

        } else {

            log.error("ERROR CREATING Customer by USERNAME : {}, AT TIME: {}", username, new Date());

            throw new CreateExistingCustomerException(ExceptionMessage.ALREADY_DELETED_CUSTOMER.getExceptionMessage());
        }

    }

    @Override
    public JwtResponse loginCustomer(LoginCustomerDto customerDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        customerDto.getCustomerUsername(),
                        customerDto.getCustomerPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isActivated()) {
            throw new CustomerNotActivatedException(ExceptionMessage.NOT_ACTIVATED.getExceptionMessage());
        }

        return new JwtResponse(jwt, userDetails.getUsername());
    }

    @Override
    @Transactional
    public boolean activateCustomerAccount(ActivateCustomerDto customerDto) {

        Optional<ActivateCustomerDto> optionalCustomerDto = Optional.ofNullable(customerDto);

        if (optionalCustomerDto.isEmpty()) {
            throw new NullActivateCustomerException(
                    ExceptionMessage.NULL_DTO_ACTIVATION.getExceptionMessage()
            );
        }

        String customerUsername = optionalCustomerDto.get().getUsername();
        BigDecimal code = optionalCustomerDto.get().getActivationCode();

        boolean isNonActivatedWithTrueCodeExists =
                customerRepository.existsByCustomerUsernameAndIsActivatedFalseAndActivationCodeEquals(customerUsername, code);

        if (isNonActivatedWithTrueCodeExists) {
            customerRepository.updateIsCustomerActivatedByUsername(customerUsername);

            log.info("ACTIVATED Customer by USERNAME: {}, AT TIME {}", customerUsername, new Date());

            return true;

        } else {

            log.error("ERROR ACTIVATE Customer BY USERNAME: {}, AT TIME: {}", customerUsername, new Date());

            throw new WrongUsernameOrCodeException(
                    ExceptionMessage.WRONG_ACTIVATION_DATA.getExceptionMessage()
            );

        }
    }
}
