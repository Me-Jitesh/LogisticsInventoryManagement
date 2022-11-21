package com.ishopee.logisticsinventorymanagement.exceptions;

import java.io.Serial;

public class ShipmentTypeNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2L;

    public ShipmentTypeNotFoundException(String message) {
        super(message);
    }
}
