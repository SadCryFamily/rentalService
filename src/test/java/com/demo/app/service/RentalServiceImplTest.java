package com.demo.app.service;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import com.demo.app.mapper.CustomerMapper;
import com.demo.app.mapper.RentalMapper;
import com.demo.app.repository.CustomerRepository;
import com.demo.app.repository.RentalRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RentalServiceImplTest {

    @Autowired
    private RentalService rentalService;

    @MockBean
    private RentalRepository rentalRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RentalMapper rentalMapper;

    private Authentication mockAuthentication;

    @MockBean
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        mockAuthentication = SecurityContextHolder.getContext().getAuthentication();
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mockAuthentication);
    }

    @Test
    @WithCustomMockUser
    public void createRental() {

        when(rentalRepository.existsByRentalCityAndRentalAddress(anyString(), anyString()))
                .thenReturn(false);

        CreateCustomerDto customerDto = CreateCustomerDto.builder()
                .customerFirstName("firstname")
                .customerLastName("lastname")
                .customerUsername("username")
                .customerEmail("email")
                .customerPassword("password").build();

        Customer mockCustomer = customerMapper.toCustomer(customerDto);

        when(customerRepository.findByCustomerUsername(anyString())).thenReturn(mockCustomer);

        CreateRentalDto expectedDto = CreateRentalDto.builder()
                .rentalName("name")
                .rentalAddress("address")
                .rentalCity("city")
                .rentalArea(54)
                .rentalPrice(BigDecimal.valueOf(5660L))
                .rentalDescription("description")
                .build();

        CreateRentalDto actualDto = rentalService.createRental(expectedDto);

        assertEquals(expectedDto.getRentalName(), actualDto.getRentalName());
        assertEquals(expectedDto.getRentalAddress(), actualDto.getRentalAddress());
        assertEquals(expectedDto.getRentalCity(), actualDto.getRentalCity());
        assertEquals(expectedDto.getRentalArea(), actualDto.getRentalArea());
        assertEquals(expectedDto.getRentalDescription(), actualDto.getRentalDescription());

    }

    @Test
    @WithCustomMockUser
    public void getAllCustomerRentals() {

        CreateRentalDto expectedDto = CreateRentalDto.builder()
                .rentalName("name")
                .rentalAddress("address")
                .rentalCity("city")
                .rentalArea(54)
                .rentalPrice(BigDecimal.valueOf(5660L))
                .rentalDescription("description")
                .build();

        Rental mockRental = rentalMapper.toRental(expectedDto);

        Set<Rental> mockRentals = Set.of(mockRental);

        when(rentalRepository.getAllRentalsByIsDeletedFalse(anyLong())).thenReturn(mockRentals);

        Set<ViewRentalDto> expectedSet = mockRentals.stream()
                .map(rental -> rentalMapper.toViewRentalDto(rental))
                .collect(Collectors.toSet());

        Set<ViewRentalDto> actualSet = rentalService.getAllCustomerRentals();

        assertEquals(expectedSet, actualSet);

    }

    @Test
    @WithCustomMockUser
    public void deleteRental() {

        CreateRentalDto expectedDto = CreateRentalDto.builder()
                .rentalName("name")
                .rentalAddress("address")
                .rentalCity("city")
                .rentalArea(54)
                .rentalPrice(BigDecimal.valueOf(5660L))
                .rentalDescription("description")
                .build();

        Rental mockRental = rentalMapper.toRental(expectedDto);

        when(rentalRepository.findById(anyLong())).thenReturn(Optional.of(mockRental));

        when(rentalRepository.existsByCustomerIdAndRentalId(anyLong(), anyLong()))
                .thenReturn(Optional.of(mockRental));

        when(customerRepository.existsByCustomerUsernameAndIsActivatedTrueAndIsDeletedFalse(anyString()))
                .thenReturn(true);

        String expectedMessage = "Rental deleted successfully";
        String actualMessage = rentalService.deleteRental(1L);

        assertEquals(expectedMessage, actualMessage);


    }
}