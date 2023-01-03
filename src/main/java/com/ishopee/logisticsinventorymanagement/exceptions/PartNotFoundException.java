package com.ishopee.logisticsinventorymanagement.exceptions;

public class PartNotFoundException extends RuntimeException {

    public PartNotFoundException() {
        super();
    }

    public PartNotFoundException(String msg) {
        super(msg);
    }
}
