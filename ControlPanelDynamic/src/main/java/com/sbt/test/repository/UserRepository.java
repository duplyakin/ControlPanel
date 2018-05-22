package com.sbt.test.repository;

import com.sbt.test.entities.User;

/**
 * Абстракция необходимых нам данных
 * Данный интерфейс убирает прямую зависимость от SpringData
 */
public interface UserRepository {

    User get(String userName) throws UserNotExistException;

    User add(User user);

    User update(User user) throws UserNotExistException;

    User delete(String username) throws UserNotExistException;

}
