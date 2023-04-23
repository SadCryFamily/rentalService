package com.demo.app.service;

import com.demo.app.dto.CreateRentalDto;
import com.demo.app.dto.PreviewRentalDto;
import com.demo.app.dto.UpdateRentalDto;
import com.demo.app.dto.ViewRentalDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface RentalService {

    CreateRentalDto createRental(CreateRentalDto rentalDto, MultipartFile photo) throws IOException;

    UpdateRentalDto updateRentalById(Long rentalId, UpdateRentalDto rentalDto, MultipartFile photo) throws IOException;

    ViewRentalDto retrieveRentalById(Long id);

    Set<PreviewRentalDto> getAllAvailableRentals();

    Set<PreviewRentalDto> getAllCustomerRentals();

    String deleteRental(Long rentalId);

}
