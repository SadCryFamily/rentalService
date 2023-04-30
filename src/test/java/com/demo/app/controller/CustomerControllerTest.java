package com.demo.app.controller;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.auth.jwt.JwtUtils;
import com.demo.app.dto.UpdateCustomerDto;
import com.demo.app.enums.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    private String jwtToken;

    @BeforeEach
    public void provideJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        jwtToken = jwtUtils.generateJwtToken(authentication);
    }

    @Test
    @Order(1)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    public void updateExistedCustomer() throws Exception {

        UpdateCustomerDto mockUpdateCustomerDto = UpdateCustomerDto.builder()
                .customerFirstName("updateFirstname")
                .customerLastName("updateLastname")
                .customerPassword("updatePassword").build();

        this.mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUpdateCustomerDto))
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.customerFirstName",
                        Matchers.is(mockUpdateCustomerDto.getCustomerFirstName())))
                .andExpect(jsonPath("$.customerLastName",
                        Matchers.is(mockUpdateCustomerDto.getCustomerLastName())))
                .andExpect(jsonPath("$.customerPassword",
                        Matchers.is(mockUpdateCustomerDto.getCustomerPassword())));

    }

    @Test
    @Order(2)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void retrieveExistedCustomer() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/profile")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.customerFirstName", Matchers.is("updateFirstname")))
                .andExpect(jsonPath("$.customerLastName", Matchers.is("updateLastname")));

    }

    @Test
    @Order(3)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void deleteExistedCustomer() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @Order(4)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void retrieveDeletedAndNotActivatedCustomer() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/profile")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.RETRIEVE_NON_EXISTED_CUSTOMER.getExceptionMessage())));

    }

    @Test
    @Order(5)
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void updateLockedCustomer() throws Exception {

        UpdateCustomerDto mockUpdateCustomerDto = UpdateCustomerDto.builder()
                .customerFirstName("updateFirstname")
                .customerLastName("updateLastname")
                .customerPassword("updatePassword").build();

        this.mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUpdateCustomerDto))
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage())));

    }

    @Order(6)
    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "updatePassword")
    public void deleteLockedCustomer() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage())));
    }

}