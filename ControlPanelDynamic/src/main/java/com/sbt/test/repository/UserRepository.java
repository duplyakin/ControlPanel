package com.sbt.test.repository;

import com.sbt.test.entities.User;

import java.util.Optional;

/**
 * Абстракция необходимых нам данных
 * Данный интерфейс убирает прямую зависимость от SpringData
 */
public interface UserRepository {

    User get(String userName);

    User add(User user);

    User update(User user);

    void delete(String username);

}
