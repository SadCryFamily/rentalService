package com.demo.app.service;

import com.demo.app.auth.service.UserDetailsImpl;
import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import com.demo.app.enums.ExceptionMessage;
import com.demo.app.exception.CreateExistingRentalException;
import com.demo.app.exception.HaveNoRentalsException;
import com.demo.app.exception.NullRentalException;
import com.demo.app.mapper.RentalMapper;
import com.demo.app.repository.CustomerRepository;
import com.demo.app.repository.RentalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public CreateRentalDto createRental(CreateRentalDto rentalDto) {

        Optional<CreateRentalDto> optionalRentalDto = Optional.ofNullable(rentalDto);

        if (optionalRentalDto.isEmpty()) {
            throw new NullRentalException(ExceptionMessage.NULL_RENTAL_CREATION.getExceptionMessage());
        }

        CreateRentalDto checkedRentalDto = optionalRentalDto.get();

        String city = checkedRentalDto.getRentalCity();
        String address = checkedRentalDto.getRentalAddress();

        if (!rentalRepository.existsByRentalCityAndRentalAddress(city, address)) {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            Rental rental = rentalMapper.toRental(checkedRentalDto);

            rentalRepository.save(rental);

            Customer customer =
                    customerRepository.findByCustomerUsername(userDetails.getUsername());

            Set<Rental> customerRentalsSet = customer.getRentals();
            customerRentalsSet.add(rental);

            customer.setRentals(customerRentalsSet);

            log.info("CREATED Rental by CITY: {}, ADDRESS: {}",
                    rental.getRentalCity(), rental.getRentalAddress());

            return checkedRentalDto;

        }

        log.error("ERROR CREATING Rental by CITY: {}, ADDRESS: {} AT TIME: {}",
                checkedRentalDto.getRentalCity(), checkedRentalDto.getRentalAddress(), new Date());

        throw new CreateExistingRentalException(ExceptionMessage.RENTAL_UNIQUE_CREATION.getExceptionMessage());

    }

    @Override
    @Transactional(readOnly = true)
    public Set<ViewRentalDto> getAllCustomerRentals() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<Rental> rentals = userDetails.getRentals();

        if (rentals.isEmpty()) {
            throw new HaveNoRentalsException(ExceptionMessage.NO_MY_RENTALS.getExceptionMessage());
        }

        return rentals.stream()
                .map(rental -> rentalMapper.toViewRentalDto(rental))
                .collect(Collectors.toSet());
    }
}