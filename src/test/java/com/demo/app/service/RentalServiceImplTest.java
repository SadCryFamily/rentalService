package com.demo.app.service;

import com.demo.app.annotation.WithCustomMockUser;
import com.demo.app.dto.CreateCustomerDto;
import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.PreviewRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import com.demo.app.mapper.CustomerMapper;
import com.demo.app.mapper.RentalMapper;
import com.demo.app.repository.CustomerRepository;
import com.demo.app.repository.RentalRepository;
import org.junit.jupiter.api.Test;
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
import java.util.*;
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
    public void retrieveRentalById() {

        ViewRentalDto expectedViewRentalDto = ViewRentalDto.builder()
                .rentalName("retrieve_name")
                .rentalAddress("retrieve_address")
                .rentalDescription("retrieve_description")
                .rentalCity("retrieve_city")
                .rentalArea(30)
                .rentalPrice(BigDecimal.TEN)
                .build();

        Optional<Rental> optionalRental = Optional.of(rentalMapper.toRental(expectedViewRentalDto));

        when(rentalRepository.findById(anyLong())).thenReturn(optionalRental);

        when(rentalRepository.existsById(anyLong())).thenReturn(true);

        ViewRentalDto actualRentalDto = rentalService.retrieveRentalById(4L);

        assertEquals(expectedViewRentalDto.getRentalName(), actualRentalDto.getRentalName());
        assertEquals(expectedViewRentalDto.getRentalAddress(), actualRentalDto.getRentalAddress());
        assertEquals(expectedViewRentalDto.getRentalDescription(), actualRentalDto.getRentalDescription());
        assertEquals(expectedViewRentalDto.getRentalCity(), actualRentalDto.getRentalCity());

    }

    @Test
    public void getAllAvailableRentals() {

        PreviewRentalDto expectedPreviewRentalDto = PreviewRentalDto.builder()
                .rentalName("preview_name")
                .rentalCity("preview_city")
                .rentalArea(30)
                .rentalPrice(BigDecimal.valueOf(1000))
                .build();

        Set<PreviewRentalDto> expectedSetRental = Set.of(expectedPreviewRentalDto);

        Rental rawRental = rentalMapper.toRental(expectedPreviewRentalDto);

        List<Rental> mockRentals = List.of(rawRental);
        when(rentalRepository.findAll()).thenReturn(mockRentals);

        Set<PreviewRentalDto> actualSetRental = rentalService.getAllAvailableRentals();

        PreviewRentalDto actualPreviewRentalDto = actualSetRental.iterator().next();

        assertEquals(expectedPreviewRentalDto.getRentalName(), actualPreviewRentalDto.getRentalName());
        assertEquals(expectedPreviewRentalDto.getRentalCity(), actualPreviewRentalDto.getRentalCity());
        assertEquals(expectedPreviewRentalDto.getRentalArea(), actualPreviewRentalDto.getRentalArea());
        assertEquals(expectedPreviewRentalDto.getRentalPrice(), actualPreviewRentalDto.getRentalPrice());

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

        Set<PreviewRentalDto> expectedSet = mockRentals.stream()
                .map(rental -> rentalMapper.toPreviewRentalDto(rental))
                .collect(Collectors.toSet());

        Set<PreviewRentalDto> actualSet = rentalService.getAllCustomerRentals();

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