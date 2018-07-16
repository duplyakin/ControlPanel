package com.sbt.test.hl.storage;

import com.sbt.test.entities.User;

import javax.persistence.EntityNotFoundException;

public interface HLEntityRepository<T extends HLEntity> extends HLConstEntityRepository<T> {

    T update(T entity,User user) throws EntityNotFoundException;

    T delete( String entityId, User user) throws EntityNotFoundException;

    T delete( T entity, User user) throws EntityNotFoundException;
}
