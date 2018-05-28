package com.sbt.test.services;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class AuthorityService extends AbstractUserService {

    private final UserRepository repo;

    @Autowired
    public AuthorityService(UserRepository repo) {
        this.repo = repo;
    }

    public User setRoles(String username, Collection<Role> roles) {
        return supplyUser(() -> {
            User user = repo.get(username);
            user.setRoles(new HashSet<>(roles));
            repo.update(user);
            return user;
        });
    }

    public User setPrivileges(String username, Collection<Privilege> privilege) {
        return supplyUser(() -> {
            User user = repo.get(username);
            user.setPrivileges(new HashSet<>(privilege));
            repo.update(user);
            return user;
        });
    }
}
