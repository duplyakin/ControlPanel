package com.sbt.test.repository.exceptions;

import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityNotFoundException;

public class UserNotExistException extends JpaObjectRetrievalFailureException {
    public UserNotExistException(String message) {
        super(new EntityNotFoundException(message));
    }
}
