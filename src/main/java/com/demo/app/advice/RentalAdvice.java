package com.demo.app.advice;

import com.demo.app.exception.CreateExistingCustomerException;
import com.demo.app.exception.CreateExistingRentalException;
import com.demo.app.exception.NullRentalException;
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

    @ExceptionHandler(NullRentalException.class)
    public ResponseEntity<Map<String, List<String>>> handleNullRentalException(NullRentalException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

}
