package com.demo.app.service;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.dto.RetrieveCustomerDto;
import com.demo.app.dto.UpdateCustomerDto;
import com.demo.app.entity.Customer;
import com.demo.app.mapper.CustomerMapper;
import com.demo.app.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerManagementServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    public void retrieveCustomer() {

        RetrieveCustomerDto expectedRetrieveCustomerDto = RetrieveCustomerDto.builder()
                .customerFirstName("retrieve_firstname")
                .customerLastName("retrieve_lastname")
                .customerUsername("retrieve_username")
                .build();

        Authentication mockAuthentication = SecurityContextHolder.getContext().getAuthentication();

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mockAuthentication);

        when(customerRepository.existsByCustomerUsernameAndIsActivatedTrueAndIsDeletedFalse(anyString()))
                .thenReturn(true);

        Customer customer = customerMapper.toCustomer(expectedRetrieveCustomerDto);

        when(customerRepository.findByCustomerUsername(anyString())).thenReturn(customer);

        RetrieveCustomerDto actualRetrieveCustomerDto = customerService.retrieveCustomer();

        assertEquals(expectedRetrieveCustomerDto.getCustomerUsername(), actualRetrieveCustomerDto.getCustomerUsername());

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    public void updateCustomer() {


        UpdateCustomerDto updateCustomerDto = UpdateCustomerDto.builder()
                .customerFirstName("update_firstname")
                .customerLastName("update_lastname")
                .customerPassword("update_password")
                .build();

        Authentication mockAuthentication = SecurityContextHolder.getContext().getAuthentication();

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mockAuthentication);

        when(customerRepository.existsByCustomerEmailOrCustomerUsername(anyString(), anyString()))
                .thenReturn(true);

        Customer mockCustomer = customerMapper.toCustomer(updateCustomerDto);

        when(customerRepository.findByCustomerUsername(anyString())).thenReturn(mockCustomer);

        UpdateCustomerDto actualCustomerDto = customerService.updateCustomer(updateCustomerDto);

        assertEquals(updateCustomerDto.getCustomerFirstName(), actualCustomerDto.getCustomerFirstName());
        assertEquals(updateCustomerDto.getCustomerLastName(), actualCustomerDto.getCustomerLastName());
        assertEquals(updateCustomerDto.getCustomerPassword(), actualCustomerDto.getCustomerPassword());

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    public void deleteCustomer() {

        Authentication mockAuthentication = SecurityContextHolder.getContext().getAuthentication();

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mockAuthentication);

        when(customerRepository.existsByCustomerEmailOrCustomerUsername(anyString(), anyString()))
                .thenReturn(true);

        when(customerRepository.existsByCustomerUsernameAndIsActivatedTrueAndIsDeletedFalse(anyString()))
                .thenReturn(true);

        boolean expectedResponse = true;
        boolean actualResponse = customerService.deleteCustomer();

        assertEquals(expectedResponse, actualResponse);

    }
}