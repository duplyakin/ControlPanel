package com.sbt.test.repository;

import com.sbt.test.entities.User;
import com.sbt.test.repository.exceptions.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Proxy cutting unnecessary spring repository methods.
 * Handy for testing, or in case of refusing spring data repository in future (i.e. using java 9+)
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

