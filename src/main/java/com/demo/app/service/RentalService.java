package com.demo.app.service;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.PreviewRentalDto;
import com.demo.app.dto.ViewRentalDto;

import java.util.Set;

public interface RentalService {

    CreateRentalDto createRental(CreateRentalDto rentalDto);

    ViewRentalDto retrieveRentalById(Long id);

    Set<PreviewRentalDto> getAllAvailableRentals();

    Set<PreviewRentalDto> getAllCustomerRentals();

    String deleteRental(Long rentalId);

}
