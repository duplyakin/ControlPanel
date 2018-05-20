package com.sbt.test.services.exceptions;

public class UserServiceException extends RuntimeException {

    public UserServiceException(String s) {
        super(s);
    }

    public UserServiceException(RuntimeException ex) {
        super(ex);
    }
}