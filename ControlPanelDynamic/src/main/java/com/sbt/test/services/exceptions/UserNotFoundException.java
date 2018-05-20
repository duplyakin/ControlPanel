package com.sbt.test.services.exceptions;

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(RuntimeException ex) {
        super(ex);
    }
}
