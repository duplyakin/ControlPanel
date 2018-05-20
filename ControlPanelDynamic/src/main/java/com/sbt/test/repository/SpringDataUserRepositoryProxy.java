package com.sbt.test.repository;

import com.sbt.test.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public User get(String userName) {
        return repo.getByUsername(userName);
    }

    @Override
    public User add(User user) {
        return repo.saveAndFlush(user);
    }

    @Override
    public void delete(String username) {
        repo.deleteByUsername(username);
    }

    @Override
    public User update(User user) {
        return repo.saveAndFlush(user);
    }
}

