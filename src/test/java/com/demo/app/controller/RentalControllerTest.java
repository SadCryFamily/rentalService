package com.demo.app.controller;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.auth.jwt.JwtUtils;
import com.demo.app.dto.CreateRentalDto;
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

import java.math.BigDecimal;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class RentalControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(1)
    public void create_non_existed_rental() throws Exception {

        CreateRentalDto mockRentalDto = CreateRentalDto.builder()
                .rentalName("rentalName")
                .rentalCity("rentalCity")
                .rentalAddress("rentalAddress")
                .rentalDescription("rentalDescription")
                .rentalArea(25)
                .rentalPrice(BigDecimal.valueOf(2500))
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRentalDto))
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalName",
                        Matchers.is(mockRentalDto.getRentalName())));

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(2)
    public void create_existed_rental() throws Exception {

        CreateRentalDto mockRentalDto = CreateRentalDto.builder()
                .rentalName("rentalName")
                .rentalCity("rentalCity")
                .rentalAddress("rentalAddress")
                .rentalDescription("rentalDescription")
                .rentalArea(25)
                .rentalPrice(BigDecimal.valueOf(2500))
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRentalDto))
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.RENTAL_UNIQUE_CREATION.getExceptionMessage())));

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(3)
    public void getAll_existed_customer_rentals() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @WithCustomMockUser(customerUsername = "NonExistedUsername", customerPassword = "NonExistedPassword")
    @Order(4)
    public void getAll_non_existed_customer_rentals() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.is(ExceptionMessage.NOT_AUTHORIZED.getExceptionMessage())));

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(5)
    public void delete_existed_rentalById() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rental")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(6)
    public void delete_non_existed_rentalById() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rental")
                        .param("id", "6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.NULL_RENTAL_CREATION.getExceptionMessage())));

    }

}