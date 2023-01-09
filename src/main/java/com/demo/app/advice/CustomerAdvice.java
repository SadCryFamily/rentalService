package com.demo.app.advice;

import com.demo.app.exception.CreateExistingCustomerException;
import com.demo.app.exception.NullCustomerException;
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
public class CustomerAdvice {

    @ExceptionHandler(CreateExistingCustomerException.class)
    public ResponseEntity<Map<String, List<String>>> handleCreateExistingCustomerException(CreateExistingCustomerException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList), new HttpHeaders(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler({NullCustomerException.class})
    public ResponseEntity<Map<String, List<String>>> handleNullCustomerException(NullCustomerException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }
}
