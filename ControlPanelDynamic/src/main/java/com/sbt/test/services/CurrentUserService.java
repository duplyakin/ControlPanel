package com.sbt.test.services;

import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import com.sbt.test.services.exceptions.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService extends AbstractUserService {

    private final UserRepository repo;

    private final PasswordEncoder encoder;

    @Autowired
    public CurrentUserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User getCurrentUser(String username) {
        return supplyUser(() -> repo.get(username));
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
            if (!encoder.matches(oldPass, user.getPassword())) {
                throw new WrongPasswordException("wrong old password");
            }
            user.setPassword(encoder.encode(newPass));
            repo.update(user);
            return user;
        });
    }
}
