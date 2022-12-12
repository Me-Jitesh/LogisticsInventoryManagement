package com.ishopee.logisticsinventorymanagement.exceptions;

public class OrderMethodNotFoundException extends RuntimeException {

    public OrderMethodNotFoundException() {
        super();
    }

    public OrderMethodNotFoundException(String msg) {
        super(msg);
    }

}
