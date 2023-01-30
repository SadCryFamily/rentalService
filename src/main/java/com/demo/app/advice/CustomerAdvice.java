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
public class CustomerAdvice {

    @ExceptionHandler(CreateExistingCustomerException.class)
    public ResponseEntity<Map<String, List<String>>> handleCreateExistingCustomerException(CreateExistingCustomerException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler({NullCustomerException.class})
    public ResponseEntity<Map<String, List<String>>> handleNullCustomerException(NullCustomerException e) {
        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({CustomerNotActivatedException.class})
    public ResponseEntity<Map<String, List<String>>> handleCustomerNotActivatedException
            (CustomerNotActivatedException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullActivateCustomerException.class})
    public ResponseEntity<Map<String, List<String>>> handleNullActivateCustomerException
            (NullActivateCustomerException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WrongUsernameOrCodeException.class})
    public ResponseEntity<Map<String, List<String>>> handleWrongUsernameOrCodeException
            (WrongUsernameOrCodeException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({SendMessageException.class})
    public ResponseEntity<Map<String, List<String>>> handleSendMessageException
            (SendMessageException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ImageFileNotFoundException.class})
    public ResponseEntity<Map<String, List<String>>> handleImageNotFoundException
            (ImageFileNotFoundException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HaveNoRentalsException.class})
    public ResponseEntity<Map<String, List<String>>> handleHaveNoRentalsException
            (HaveNoRentalsException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullUpdateCustomerException.class})
    public ResponseEntity<Map<String, List<String>>> handleNullUpdateCustomerException
            (NullUpdateCustomerException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UpdateNonExistingCustomerException.class})
    public ResponseEntity<Map<String, List<String>>> handleUpdateNonExistingCustomerException
            (UpdateNonExistingCustomerException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CustomerAlreadyDeletedException.class})
    public ResponseEntity<Map<String, List<String>>> handleCustomerAlreadyDeletedException
            (CustomerAlreadyDeletedException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DeleteNonExistingCustomerException.class})
    public ResponseEntity<Map<String, List<String>>> handleDeleteNonExistingCustomerException
            (DeleteNonExistingCustomerException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccountLockedException.class})
    public ResponseEntity<Map<String, List<String>>> handleAccountLockedException (AccountLockedException e) {

        List<String> errorsList = Collections.singletonList(e.getMessage());

        return new ResponseEntity<>(ErrorsMapperUtil.getErrorsMap(errorsList),
                new HttpHeaders(), HttpStatus.LOCKED);
    }

}
