package com.ishopee.logisticsinventorymanagement.exceptions;

import java.io.Serial;

public class UserInfoNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2L;

    public UserInfoNotFoundException(String message) {
        super(message);
    }
}
