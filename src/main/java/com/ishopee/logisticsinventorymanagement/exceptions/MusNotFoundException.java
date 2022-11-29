package com.ishopee.logisticsinventorymanagement.exceptions;

import java.io.Serial;

public class MusNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MusNotFoundException(String message) {
        super(message);
    }
}
