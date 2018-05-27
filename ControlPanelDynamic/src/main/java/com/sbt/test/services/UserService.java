package com.sbt.test.services;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import com.sbt.test.services.exceptions.UserNotFoundException;
import com.sbt.test.services.exceptions.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class UserService extends AbstractUserService {

    private final UserRepository repo;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        super(encoder);
        this.repo = repo;
    }

    public User get(String username) {
        return supplyUserOrThrow(() -> repo.get(username), UserNotFoundException::new);
    }

    public User add(User user) {
        return supplyUser(() -> repo.add(encodeUser(user)));
    }

    public User update(User user) {
        return supplyUser(() -> repo.update(user));
    }

    public User delete(String username) {
        return supplyUser(() -> repo.delete(username));
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

    public User changePassword(String username, String oldPass, String newPass) {
        return supplyUser(() -> {
            if (oldPass == null) {
                throw new WrongPasswordException("null old password");
            }
            if (newPass == null) {
                throw new WrongPasswordException("null new password");
            }
            User user = repo.get(username);
            if (!matches(oldPass, user.getPassword())) {
                throw new WrongPasswordException("wrong old password");
            }
            user.setPassword(encode(newPass));
            repo.update(user);
            return user;
        });
    }
}
