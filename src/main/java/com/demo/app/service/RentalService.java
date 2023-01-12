package com.demo.app.service;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.ViewRentalDto;

import java.util.Set;

public interface RentalService {

    CreateRentalDto createRental(CreateRentalDto rentalDto);

    Set<ViewRentalDto> getAllCustomerRentals();

}
