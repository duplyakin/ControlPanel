package com.sbt.test.repository;

import com.sbt.test.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Прокси, отсекающий те методы репозитория spring data, которые не нужны.
 * Полезен для unit-тестирования, и в случае если возникнет необходимость отказаться от SpringData
 */
@Repository
public class SpringDataUserRepositoryProxy implements UserRepository {

    private final SpringDataUserRepository repo;

    @Autowired
    public SpringDataUserRepositoryProxy(SpringDataUserRepository repository) {
        this.repo = repository;
    }


    @Override
    public Optional<User> getByUsername(String userName) {
        return repo.getByUsername(userName);
    }

    @Override
    public void deleteByUsername(String username) {
        repo.deleteByUsername(username);
    }

    @Override
    public User update(User user) {
        return repo.saveAndFlush(user);
    }
}

