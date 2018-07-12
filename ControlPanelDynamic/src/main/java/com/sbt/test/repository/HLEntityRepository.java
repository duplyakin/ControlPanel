package com.sbt.test.repository;

import com.sbt.test.entities.User;
import com.sbt.test.repository.exceptions.UserNotExistException;

import javax.persistence.EntityNotFoundException;

public interface HLEntityRepository<T> extends HLConstEntityRepository<T> {

    T update(T entity,User user) throws EntityNotFoundException;

    T delete( String entityId, User user) throws EntityNotFoundException;

    T delete( T entity, User user) throws EntityNotFoundException;
}
