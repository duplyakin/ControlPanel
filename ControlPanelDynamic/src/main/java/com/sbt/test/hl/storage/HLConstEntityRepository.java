package com.sbt.test.hl.storage;

import com.sbt.test.entities.User;

import javax.persistence.EntityNotFoundException;

public interface HLConstEntityRepository<T extends HLEntity> {
    T getFromHl(String hlEntityId, User user) throws EntityNotFoundException;
    T get (Long id);
    T addToHl(T entity,User user);
}
