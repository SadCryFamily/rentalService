package com.demo.app.service;

import com.demo.app.dto.*;
import com.demo.app.entity.Customer;
import com.demo.app.entity.Rental;
import com.demo.app.enums.ExceptionMessage;
import com.demo.app.exception.*;
import com.demo.app.mapper.RentalMapper;
import com.demo.app.repository.CustomerRepository;
import com.demo.app.repository.RentalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
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

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Override
    @Transactional
    public CreateRentalDto createRental(CreateRentalDto rentalDto, MultipartFile photo) throws IOException {

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

            if (userDetails.isAccountNonLocked()) {

                Rental rental = rentalMapper.toRental(checkedRentalDto);

                String photoUrl = firebaseStorageService.uploadFile(photo);
                rental.setPhotoUrl(photoUrl);

                rentalRepository.save(rental);

                Customer customer =
                        customerRepository.findByCustomerUsername(userDetails.getUsername());

                Set<Rental> customerRentalsSet = customer.getRentals();
                customerRentalsSet.add(rental);

                customer.setRentals(customerRentalsSet);

                log.info("CREATED Rental by CITY: {}, ADDRESS: {}",
                        rental.getRentalCity(), rental.getRentalAddress());

                return checkedRentalDto;

            } else {

                log.error("ERROR CREATING Rental by CITY: {}, ADDRESS: {} AT TIME: {}",
                        checkedRentalDto.getRentalCity(), checkedRentalDto.getRentalAddress(), new Date());

                throw new AccountLockedException(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage());

            }

        }

        log.error("ERROR CREATING Rental by CITY: {}, ADDRESS: {} AT TIME: {}",
                checkedRentalDto.getRentalCity(), checkedRentalDto.getRentalAddress(), new Date());

        throw new CreateExistingRentalException(ExceptionMessage.RENTAL_UNIQUE_CREATION.getExceptionMessage());

    }

    @Override
    public UpdateRentalDto updateRentalById(Long rentalId, UpdateRentalDto rentalDto, MultipartFile photo) throws IOException {

        Optional<Rental> optionalRental = rentalRepository.findByRentalIdAndIsDeletedFalse(rentalId);

        if (optionalRental.isEmpty()) {
            throw new NullRentalException(ExceptionMessage.NULL_RENTAL_CREATION.getExceptionMessage());
        }

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Long customerId = userDetails.getCustomerId();

        if (rentalRepository.findByCustomerIdAndRentalId(customerId, rentalId).isEmpty()) {
            throw new NotCustomerRentalException(ExceptionMessage.NOT_CUSTOMER_RENTAL.getExceptionMessage());
        }

        Rental checkedRental = optionalRental.get();

        String updatedUrl = firebaseStorageService.uploadFile(photo);

        checkedRental.setPhotoUrl(updatedUrl);
        checkedRental.setRentalName(rentalDto.getRentalName());
        checkedRental.setRentalDescription(rentalDto.getRentalDescription());
        checkedRental.setRentalPrice(rentalDto.getRentalPrice());

        rentalRepository.save(checkedRental);

        return rentalDto;

    }

    @Override
    @Transactional(readOnly = true)
    public ViewRentalDto retrieveRentalById(Long id) {

        Optional<Rental> optionalRental = rentalRepository.findByRentalIdAndIsDeletedFalse(id);

        if (optionalRental.isEmpty()) {
            throw new NullRentalException(ExceptionMessage.RETRIEVE_NULL_RENTAL.getExceptionMessage());
        }

        ViewRentalDto viewRentalDto = rentalMapper.toViewRentalDto(optionalRental.get());

        if (!rentalRepository.existsById(id)) {
            log.error("ERROR RETRIEVE Rental By ID: {}", id);
            throw new NullRentalException(ExceptionMessage.RETRIEVE_NULL_RENTAL.getExceptionMessage());
        }

        log.info("RETRIEVED Rental By ID: {}, NAME: {}", id, viewRentalDto.getRentalName());
        return viewRentalDto;

    }

    @Override
    @Transactional(readOnly = true)
    public Set<PreviewRentalDto> getAllAvailableRentals() {

        if (rentalRepository.findAllByIsDeletedFalse().size() == 0) {
            throw new NonExistingAllRentalsException(ExceptionMessage.NO_ALL_RENTALS.getExceptionMessage());
        }

        return rentalRepository.findAllByIsDeletedFalse()
                .stream()
                .map(entity -> rentalMapper.toPreviewRentalDto(entity))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PreviewRentalDto> getAllCustomerRentals() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (userDetails.isAccountNonLocked()) {

            Long customerId = userDetails.getCustomerId();

            Set<Rental> rentals = rentalRepository.getAllRentalsByIsDeletedFalse(customerId);

            if (rentals.isEmpty()) {
                throw new HaveNoRentalsException(ExceptionMessage.NO_MY_RENTALS.getExceptionMessage());
            }

            return rentals.stream()
                    .map(rental -> rentalMapper.toPreviewRentalDto(rental))
                    .collect(Collectors.toSet());
        }

        log.error("ERROR GETTING Rental for Customer by USERNAME: {}, AT TIME: {}",
                userDetails.getUsername(), new Date());

        throw new AccountLockedException(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage());

    }

    @Override
    @Transactional
    public String deleteRental(Long rentalId) {

        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);

        if (optionalRental.isEmpty()) {
            throw new NullRentalException(ExceptionMessage.NULL_RENTAL_CREATION.getExceptionMessage());
        }

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Long customerId = userDetails.getCustomerId();

        if (rentalRepository.findByCustomerIdAndRentalId(customerId, rentalId).isEmpty()) {
            throw new DeleteNonExistingRentalException(ExceptionMessage.NON_EXISTED_RENTAL.getExceptionMessage());
        }

        if (customerRepository.existsByCustomerUsernameAndIsActivatedTrueAndIsDeletedFalse(username)) {

            rentalRepository.updateIsDeletedByRentalId(rentalId);

            log.info("DELETE Rental for Customer by USERNAME: {}, AT TIME: {}", username, new Date());

            return "Rental deleted successfully";
        }

        log.error("ERROR DELETE Rental for Customer by USERNAME: {}, AT TIME: {}",
                username, new Date());

        throw new AccountLockedException(ExceptionMessage.LOCKED_CUSTOMER_ACCOUNT.getExceptionMessage());
    }
}
