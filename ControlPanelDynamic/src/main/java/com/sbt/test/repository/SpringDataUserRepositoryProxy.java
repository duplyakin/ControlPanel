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

    private UserNotExistException userNotExistException(String username) {
        return new UserNotExistException("User " + username + " does not exist");
    }

    @Override
    public User get(String userName) {
        User user = repo.getByUsername(userName);
        if (user == null) {
            throw userNotExistException(userName);
        }
        return user;
    }

    @Override
    public User add(User user) {
        return repo.saveAndFlush(user);
    }

    @Override
    public User delete(String username) {
        User user = repo.getByUsername(username);
        if (user == null) {
            throw userNotExistException(username);
        }
        repo.deleteByUsername(username);
        return user;
    }

    @Override
    public User update(User user) {
        if (repo.getByUsername(user.getUsername()) == null) {
            throw new UserNotExistException("User " + user.getUsername() + " does not exist");
        }
        return repo.saveAndFlush(user);
    }
}

