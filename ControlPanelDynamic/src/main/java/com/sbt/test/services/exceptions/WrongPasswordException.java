package com.sbt.test.services.exceptions;

public class WrongPasswordException extends UserServiceException {

    public WrongPasswordException(String s) {
        super(s);
    }
}
