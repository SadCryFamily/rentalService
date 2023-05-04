package com.demo.app.advice;

import com.demo.app.exception.*;
import com.demo.app.util.ErrorsMapperUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RentalAdvice {

    @ExceptionHandler(CreateExistingRentalException.class)
    public ResponseEntity<Map<String, List<String>>> handleCreateExistingRentalException(CreateExistingRentalException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullRentalException.class})
    public ResponseEntity<Map<String, List<String>>> handleNullRentalException(NullRentalException e) {
        List<String> errorsList = Collections.singletonList(e.getLocalizedMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RetrieveNullRentalException.class)
    public ResponseEntity<Map<String, List<String>>> handleRetrieveNullRentalException
            (RetrieveNullRentalException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonExistingAllRentalsException.class)
    public ResponseEntity<Map<String, List<String>>> handleNonExistingAllRentalsException
            (NonExistingAllRentalsException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteNonExistingRentalException.class)
    public ResponseEntity<Map<String, List<String>>> handleDeleteNonExistingRentalException
            (DeleteNonExistingRentalException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotCustomerRentalException.class)
    public ResponseEntity<Map<String, List<String>>> handleNotCustomerRentalException
            (NotCustomerRentalException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPhotoException.class)
    public ResponseEntity<Map<String, List<String>>> handleNullPhotoException
            (NullPhotoException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
