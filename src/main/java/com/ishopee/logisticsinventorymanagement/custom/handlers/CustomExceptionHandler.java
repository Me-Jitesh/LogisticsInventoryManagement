package com.ishopee.logisticsinventorymanagement.custom.handlers;

import com.ishopee.logisticsinventorymanagement.custom.error.ErrorType;
import com.ishopee.logisticsinventorymanagement.exceptions.MusNotFoundException;
import com.ishopee.logisticsinventorymanagement.exceptions.OrderMethodNotFoundException;
import com.ishopee.logisticsinventorymanagement.exceptions.ShipmentTypeNotFoundException;
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

    @ExceptionHandler(ShipmentTypeNotFoundException.class)
    public ResponseEntity<ErrorType> handleShipementNotFound(ShipmentTypeNotFoundException stnfe) {
        return new ResponseEntity<>(new ErrorType(new Date().toString(), "Shipment Type", stnfe.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderMethodNotFoundException.class)
    public ResponseEntity<ErrorType> handleOrderMethodNotFound(OrderMethodNotFoundException omnfe) {
        return new ResponseEntity<>(new ErrorType(new Date().toString(), "Order Method", omnfe.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
