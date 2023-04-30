package com.demo.app.service;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.auth.entity.CustomerRoles;
import com.demo.app.auth.entity.Role;
import com.demo.app.auth.jwt.JwtUtils;
import com.demo.app.auth.pojo.JwtResponse;
import com.demo.app.auth.repository.RoleRepository;
import com.demo.app.dto.ActivateCustomerDto;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.LoginCustomerDto;
import com.demo.app.dto.ResendActivationDto;
import com.demo.app.entity.Customer;
import com.demo.app.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import com.demo.app.repository.CustomerRepository;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private ActivationService activationService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    public void loginCustomer() {

        String templateJwtToken =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyTmFtZSIsImlhdCI6MTY3NDA" +
                        "1MDAzOCwiZXhwIjoxNjc0MDg2MDM4fQ.5O0L4CTV7Y2zRgAFT2UjnQ244" +
                        "-9LDYVIaIzNFVWWmmlvPacNHI-ofP-4LSIPSSAVmqiXXKobN6pmoT8hEQLCTQ";

        Authentication customAuthentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl mockUserDetails = (UserDetailsImpl) customAuthentication.getPrincipal();

        LoginCustomerDto customerDto = LoginCustomerDto.builder()
                .customerUsername(mockUserDetails.getUsername())
                .customerPassword(mockUserDetails.getPassword())
                .build();

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(customAuthentication);

        when(jwtUtils.generateJwtToken(any(Authentication.class)))
                .thenReturn(templateJwtToken);

        JwtResponse expectedJwtResponse =
                new JwtResponse(templateJwtToken, customerDto.getCustomerUsername());

        JwtResponse actualJwtResponse = authService.loginCustomer(customerDto);

        assertEquals(expectedJwtResponse.getJwtToken(), actualJwtResponse.getJwtToken());
        assertEquals(expectedJwtResponse.getUsername(), actualJwtResponse.getUsername());

    }

    @Test
    public void registerCustomer() throws MessagingException, FileNotFoundException {

        CreateCustomerDto customerDto = CreateCustomerDto.builder()
                .customerFirstName("firstname")
                .customerLastName("lastname")
                .customerUsername("username")
                .customerEmail("email")
                .customerPassword("password")
                .build();

        when(customerRepository.existsByCustomerEmailOrCustomerUsername(anyString(), anyString()))
                .thenReturn(false);

        Role mockRole = new Role(1L, CustomerRoles.ROLE_USER);

        when(roleRepository.findByRoleName(any(CustomerRoles.class))).thenReturn(mockRole);

        Mockito.doNothing().when(activationService).saveActivationCode(anyString(), any(BigDecimal.class));
        Mockito.verify(activationService, Mockito.never()).saveActivationCode(anyString(), any(BigDecimal.class));

        BigDecimal mockActivationCode = new BigDecimal("444333");

        when(emailService.sendActivationEmail(anyString())).thenReturn(mockActivationCode);

        String expectedMessage = "Customer successfully created!";
        String actualMessage = authService.registerCustomer(customerDto);

        assertEquals(expectedMessage, actualMessage);


    }

    @Test
    public void activateCustomerAccount() {

        BigDecimal testableCode = new BigDecimal("444555");

        ActivateCustomerDto customerDto = ActivateCustomerDto.builder()
                .username("username")
                .activationCode(testableCode)
                .build();

        when(customerRepository.existsByCustomerUsernameAndIsActivatedFalse(anyString())).thenReturn(true);

        when(activationService.isActivationCodeValid(anyString())).thenReturn(true);

        when(activationService.retrieveActivationCode(anyString())).thenReturn(testableCode);

        boolean expectedResponse = true;
        boolean actualResponse = authService.activateCustomerAccount(customerDto);

        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void restoreActivationCode() throws MessagingException, FileNotFoundException {

        when(customerRepository.existsByCustomerEmailAndIsActivatedFalse(anyString()))
                .thenReturn(true);

        BigDecimal testableCode = new BigDecimal("123456");
        when(emailService.sendActivationEmail(anyString())).thenReturn(testableCode);

        CreateCustomerDto customerDto = CreateCustomerDto.builder()
                .customerFirstName("Testable")
                .customerLastName("Testable")
                .customerUsername("originskull")
                .customerEmail("testable@gmail.com")
                .customerPassword("444555").build();

        Customer testableCustomer = customerMapper.toCustomer(customerDto);
        when(customerRepository.findByCustomerEmail(anyString())).thenReturn(testableCustomer);

        Mockito.doNothing().when(activationService).saveActivationCode(anyString(), any(BigDecimal.class));

        ResendActivationDto resendActivationDto = ResendActivationDto.builder()
                .customerEmail("testable@gmail.com")
                .build();

        boolean expectedResponse = true;
        boolean actualResponse = authService.restoreActivationCode(resendActivationDto);

        assertEquals(expectedResponse, actualResponse);
    }
}