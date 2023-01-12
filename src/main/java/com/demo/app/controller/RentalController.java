package com.demo.app.controller;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.entity.Rental;
import com.demo.app.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rental")
    public CreateRentalDto createRental(@RequestBody @Valid CreateRentalDto rentalDto) {
        return rentalService.createRental(rentalDto);
    }



}
