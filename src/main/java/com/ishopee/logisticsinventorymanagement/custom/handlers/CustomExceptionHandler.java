package com.ishopee.logisticsinventorymanagement.custom.handlers;

import com.ishopee.logisticsinventorymanagement.custom.error.ErrorType;
import com.ishopee.logisticsinventorymanagement.exceptions.MusNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MusNotFoundException.class)
    public ResponseEntity<ErrorType> handleMusNotFound(MusNotFoundException mnfe) {
        return new ResponseEntity<>(new ErrorType(new Date().toString(), "MUS", mnfe.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
