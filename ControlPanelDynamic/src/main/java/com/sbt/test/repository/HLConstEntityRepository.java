package com.sbt.test.repository;

import com.sbt.test.entities.User;
import com.sbt.test.repository.exceptions.UserNotExistException;

import javax.persistence.EntityNotFoundException;

public interface HLConstEntityRepository<T> {
    T get(String entityId) throws EntityNotFoundException;

    T add(T entity,User user);
}
