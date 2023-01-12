package com.demo.app.controller;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rental")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRentalDto createRental(@RequestBody @Valid CreateRentalDto rentalDto) {
        return rentalService.createRental(rentalDto);
    }

    @GetMapping("/rental")
    @ResponseStatus(HttpStatus.FOUND)
    public Set<ViewRentalDto> getAllCustomerRentals() {
        return rentalService.getAllCustomerRentals();
    }

}
