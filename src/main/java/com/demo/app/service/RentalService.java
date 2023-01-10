package com.demo.app.service;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.entity.Rental;

public interface RentalService {

    Rental createRental(CreateRentalDto rentalDto);

}
