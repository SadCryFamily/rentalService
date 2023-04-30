package com.demo.app.controller;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.auth.jwt.JwtUtils;
import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.UpdateRentalDto;
import com.demo.app.enums.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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

    private String jwtToken;

    @BeforeEach
    public void provideJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        jwtToken = jwtUtils.generateJwtToken(authentication);
    }

    private MockMultipartFile createMockFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("images/company_logo.png");

        File file = classPathResource.getFile();

        return new MockMultipartFile("photo",
                "company_logo.png",
                "image/png",
                Files.readAllBytes(file.toPath()));
    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(1)
    public void createNonExistedRental() throws Exception {

        CreateRentalDto mockRentalDto = CreateRentalDto.builder()
                .rentalName("rentalName")
                .rentalCity("rentalCity")
                .rentalAddress("rentalAddress")
                .rentalDescription("rentalDescription")
                .rentalArea(25)
                .rentalPrice(BigDecimal.valueOf(2500))
                .build();

        MockPart photoPart = new MockPart("photo", "company_logo.png",createMockFile().getBytes());
        photoPart.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);

        MockPart rentalPart = new MockPart("rental" , objectMapper.writeValueAsString(mockRentalDto).getBytes());
        rentalPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(multipart("/rental")
                .part(photoPart, rentalPart)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(2)
    public void createExistedRental() throws Exception {

        CreateRentalDto mockRentalDto = CreateRentalDto.builder()
                .rentalName("rentalName")
                .rentalCity("rentalCity")
                .rentalAddress("rentalAddress")
                .rentalDescription("rentalDescription")
                .rentalArea(25)
                .rentalPrice(BigDecimal.valueOf(2500))
                .build();

        MockPart photoPart = new MockPart("photo", "company_logo.png", createMockFile().getBytes());
        photoPart.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);

        MockPart rentalPart = new MockPart("rental" , objectMapper.writeValueAsString(mockRentalDto).getBytes());
        rentalPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(multipart("/rental")
                        .part(photoPart, rentalPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.RENTAL_UNIQUE_CREATION.getExceptionMessage())));

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(3)
    public void retrieveRentalById() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rental")
                        .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.rentalName", Matchers.is("rentalName")))
                .andExpect(jsonPath("$.rentalCity", Matchers.is("rentalCity")));

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(4)
    public void retrieveAllAvailableRentals() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rentals")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(5)
    public void getAllExistedCustomerRentals() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @WithCustomMockUser(customerUsername = "NonExistedUsername", customerPassword = "NonExistedPassword")
    @Order(6)
    public void getAllNonExistedCustomerRentals() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/my")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        Matchers.is(ExceptionMessage.NOT_AUTHORIZED.getExceptionMessage())));

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(7)
    public void updateExistedRentalById() throws Exception {

        UpdateRentalDto mockUpdateDto = UpdateRentalDto.builder()
                .rentalName("updateName")
                .rentalDescription("updateDescription")
                .rentalPrice(BigDecimal.valueOf(6600)).build();

        MockPart photoPart = new MockPart("photo", "company_logo.png", createMockFile().getBytes());
        photoPart.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);

        MockPart rentalPart = new MockPart("rental" , objectMapper.writeValueAsString(mockUpdateDto).getBytes());
        rentalPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/rental")
                        .part(photoPart, rentalPart)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(8)
    public void updateNonExistedRentalById() throws Exception {

        UpdateRentalDto mockUpdateDto = UpdateRentalDto.builder()
                .rentalName("updateName")
                .rentalDescription("updateDescription")
                .rentalPrice(BigDecimal.valueOf(6600)).build();

        MockPart photoPart = new MockPart("photo", "company_logo.png", createMockFile().getBytes());
        photoPart.getHeaders().setContentType(MediaType.MULTIPART_FORM_DATA);

        MockPart rentalPart = new MockPart("rental" , objectMapper.writeValueAsString(mockUpdateDto).getBytes());
        rentalPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/rental")
                        .part(photoPart, rentalPart)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .param("id", "44"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.NULL_RENTAL_CREATION.getExceptionMessage())));

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(9)
    public void deleteExistedRentalById() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rental")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    @WithCustomMockUser(customerUsername = "username", customerPassword = "password")
    @Order(10)
    public void deleteNonExistedRentalById() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rental")
                        .param("id", "6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(jsonPath("$.errors[0]",
                        Matchers.is(ExceptionMessage.NULL_RENTAL_CREATION.getExceptionMessage())));

    }

}