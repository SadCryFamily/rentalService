package com.demo.app.controller;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.PreviewRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rental")
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRentalDto createRental(@RequestBody @Valid CreateRentalDto rentalDto) {
        return rentalService.createRental(rentalDto);
    }

    @GetMapping("/rentals")
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Set<PreviewRentalDto> getAllAvailableRentals() {
        return rentalService.getAllAvailableRentals();
    }

    @GetMapping("/rental")
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public ViewRentalDto retrieveRentalById(@RequestParam("id") Long rentalId) {
        return rentalService.retrieveRentalById(rentalId);
    }

    @GetMapping("/my")
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Set<PreviewRentalDto> getAllCustomerRentals() {
        return rentalService.getAllCustomerRentals();
    }

    @DeleteMapping("/rental")
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRentalById(@RequestParam("id") Long rentalId) {
        return rentalService.deleteRental(rentalId);
    }

}
