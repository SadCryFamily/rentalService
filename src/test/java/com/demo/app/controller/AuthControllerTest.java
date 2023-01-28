package com.demo.app.controller;

import com.demo.app.dto.ActivateCustomerDto;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.LoginCustomerDto;
import com.demo.app.enums.ExceptionMessage;
import com.demo.app.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void register_new_customer() throws Exception {

        CreateCustomerDto mockCustomerDto = CreateCustomerDto.builder()
                .customerFirstName("firstname")
                .customerLastName("lastname")
                .customerEmail("fogger.work@gmail.com")
                .customerUsername("username")
                .customerPassword("password").build();

        BigDecimal mockActivationCode = new BigDecimal("222222");

        Mockito.when(emailService.sendActivationEmail(Mockito.anyString()))
                .thenReturn(mockActivationCode);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCustomerDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @Order(2)
    public void register_existed_customer() throws Exception {

        CreateCustomerDto mockCustomerDto = CreateCustomerDto.builder()
                .customerFirstName("firstname")
                .customerLastName("lastname")
                .customerEmail("fogger.work@gmail.com")
                .customerUsername("username")
                .customerPassword("password").build();

        BigDecimal mockActivationCode = new BigDecimal("222222");

        Mockito.when(emailService.sendActivationEmail(Mockito.anyString()))
                .thenReturn(mockActivationCode);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCustomerDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.ALREADY_DELETED_CUSTOMER.getExceptionMessage())));
    }

    @Test
    @Order(3)
    public void activate_non_activated_customer() throws Exception {

        ActivateCustomerDto mockActivateCustomerDto = ActivateCustomerDto.builder()
                .activationCode(new BigDecimal("222222"))
                .username("username").build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockActivateCustomerDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @Order(4)
    public void activate_activated_customer() throws Exception {

        ActivateCustomerDto mockActivateCustomerDto = ActivateCustomerDto.builder()
                .activationCode(new BigDecimal("222222"))
                .username("username").build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockActivateCustomerDto)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.WRONG_ACTIVATION_DATA.getExceptionMessage())));

    }

    @Test
    @Order(5)
    public void login_non_existed_customer() throws Exception {

        LoginCustomerDto mockLoginCustomerDto = LoginCustomerDto.builder()
                .customerUsername("nonExistedUsername")
                .customerPassword("nonExistedPassword").build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockLoginCustomerDto)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    @Order(6)
    public void login_existed_customer() throws Exception {

        LoginCustomerDto mockLoginCustomerDto = LoginCustomerDto.builder()
                .customerUsername("username")
                .customerPassword("password").build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockLoginCustomerDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

}