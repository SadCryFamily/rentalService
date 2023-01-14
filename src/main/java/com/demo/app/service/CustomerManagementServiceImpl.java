package com.demo.app.service;

import com.demo.app.auth.service.UserDetailsImpl;
import com.demo.app.dto.UpdateCustomerDto;
import com.demo.app.entity.Customer;
import com.demo.app.enums.ExceptionMessage;
import com.demo.app.exception.*;
import com.demo.app.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class CustomerManagementServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Override
    @Transactional
    public UpdateCustomerDto updateCustomer(UpdateCustomerDto customerDto) {

        Optional<UpdateCustomerDto> optionalUpdateCustomerDto =
                Optional.ofNullable(customerDto);

        if (optionalUpdateCustomerDto.isEmpty()) {
            throw new NullUpdateCustomerException(ExceptionMessage.NULL_CUSTOMER_UPDATE.getExceptionMessage());
        }

        UpdateCustomerDto checkedCustomerDto = optionalUpdateCustomerDto.get();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String username = userDetails.getUsername();
        String email = userDetails.getCustomerEmail();

        if (!userDetails.isAccountNonLocked()) {
            log.error("ERROR UPDATE Customer by USERNAME: {}, AT TIME: {}", username, new Date());

            throw new AccountLockedException(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage());
        }

        if (customerRepository.existsByCustomerEmailOrCustomerUsername(email, username)) {

            Customer existingCustomer =
                    customerRepository.findByCustomerUsername(userDetails.getUsername());

            existingCustomer.setCustomerFirstName(checkedCustomerDto.getCustomerFirstName());
            existingCustomer.setCustomerLastName(checkedCustomerDto.getCustomerLastName());

            String encryptedPassword = cryptPasswordEncoder.encode(checkedCustomerDto.getCustomerPassword());
            existingCustomer.setCustomerPassword(encryptedPassword);

            log.info("UPDATED Customer by ID: {}, AT TIME: {}",
                    existingCustomer.getCustomerId(), existingCustomer.getUpdatedAt());

            return checkedCustomerDto;

        }

        log.error("ERROR UPDATE Customer by USERNAME: {}, AT TIME: {}", username, new Date());
        throw new UpdateNonExistingCustomerException(ExceptionMessage.NON_EXISTED_CUSTOMER.getExceptionMessage());

    }

    @Override
    @Transactional
    public boolean deleteCustomer() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String email = userDetails.getCustomerEmail();
        String username = userDetails.getUsername();

        if (!userDetails.isAccountNonLocked()) {
            log.error("ERROR UPDATE Customer by USERNAME: {}, AT TIME: {}", username, new Date());

            throw new AccountLockedException(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage());
        }

        if (customerRepository.existsByCustomerEmailOrCustomerUsername(email, username)) {

            boolean isCustomerExistsAndNotDeleted =
                    customerRepository.existsByCustomerUsernameAndIsActivatedTrueAndIsDeletedFalse(username);

            if (isCustomerExistsAndNotDeleted) {

                customerRepository.updateIsCustomerDeletedByUsername(username);

                SecurityContextHolder.getContext().setAuthentication(null);

                log.info("DELETED Customer by USERNAME: {}, AT TIME: {}", username, new Date());

                return true;

            } else {

                log.error("ERROR DELETE Customer by USERNAME: {}, AT TIME{}", username, new Date());

                throw new CustomerAlreadyDeletedException
                        (ExceptionMessage.ALREADY_DELETED_CUSTOMER.getExceptionMessage());
            }

        }

        log.error("ERROR DELETE Customer by USERNAME: {}, AT TIME: {}", username, new Date());

        throw new DeleteNonExistingCustomerException
                (ExceptionMessage.DELETE_NON_EXISTED_CUSTOMER.getExceptionMessage());

    }
}
