package com.sbt.test.repository;

import com.sbt.test.entities.User;

import java.util.Optional;

/**
 * Абстракция необходимых нам данных
 * Данный интерфейс убирает прямую зависимость от SpringData
 */
public interface UserRepository {

    Optional<User> getByUsername(String userName);

    void deleteByUsername(String username);

    User saveAndFlush(User user);

}
