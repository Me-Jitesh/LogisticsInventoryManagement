package com.ishopee.logisticsinventorymanagement.custom.handlers;

import com.ishopee.logisticsinventorymanagement.exceptions.MusNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MusNotFoundException.class)
    public ResponseEntity<String> handleMusNotFound(MusNotFoundException mnfe) {
        return new ResponseEntity<>(mnfe.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
