package com.demo.app.controller;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.PreviewRentalDto;
import com.demo.app.dto.UpdateRentalDto;
import com.demo.app.dto.ViewRentalDto;
import com.demo.app.repository.RentalRepository;
import com.demo.app.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private RentalRepository rentalRepository;

    @PostMapping(value = "/rental")
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRentalDto createRental(@RequestPart ("photo") MultipartFile photo,
                                        @RequestPart ("rental") @Valid CreateRentalDto rentalDto) throws IOException {
        return rentalService.createRental(rentalDto, photo);
    }

    @PutMapping(value = "/rental")
    @PreAuthorize(value = "hasRole('USER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UpdateRentalDto updateRentalById(@RequestParam ("id") Long rentalId,
                                            @RequestPart ("rental") @Valid UpdateRentalDto rentalDto,
                                            @RequestPart ("photo") MultipartFile photo) throws IOException {
        return rentalService.updateRentalById(rentalId, rentalDto, photo);
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
