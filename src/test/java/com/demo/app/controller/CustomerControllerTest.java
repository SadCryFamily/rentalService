package com.demo.app.controller;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.auth.jwt.JwtUtils;
import com.demo.app.dto.UpdateCustomerDto;
import com.demo.app.enums.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    public void update_existed_customer() throws Exception {

        UpdateCustomerDto mockUpdateCustomerDto = UpdateCustomerDto.builder()
                .customerFirstName("updateFirstname")
                .customerLastName("updateLastname")
                .customerPassword("updatePassword").build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUpdateCustomerDto))
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerFirstName",
                        Matchers.is(mockUpdateCustomerDto.getCustomerFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerLastName",
                        Matchers.is(mockUpdateCustomerDto.getCustomerLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerPassword",
                        Matchers.is(mockUpdateCustomerDto.getCustomerPassword())));

    }

    @Test
    @Order(2)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void delete_existed_customer() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @Order(3)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void update_locked_customer() throws Exception {

        UpdateCustomerDto mockUpdateCustomerDto = UpdateCustomerDto.builder()
                .customerFirstName("updateFirstname")
                .customerLastName("updateLastname")
                .customerPassword("updatePassword").build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUpdateCustomerDto))
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage())));

    }

    @Order(4)
    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void delete_locked_customer() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage())));
    }

}