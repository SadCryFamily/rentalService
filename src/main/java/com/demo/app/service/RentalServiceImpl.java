package com.demo.app.service;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.entity.Rental;
import com.demo.app.mapper.RentalMapper;
import com.demo.app.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Rental createRental(CreateRentalDto rentalDto) {
        Rental rental = rentalMapper.toRental(rentalDto);

        Rental savedRental = rentalRepository.save(rental);

        return savedRental;
    }
}
