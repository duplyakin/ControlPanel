package com.sbt.test.services;

import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import com.sbt.test.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractUserService {

    private final UserRepository repo;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    private User encodeUser(User user) {
        return User.builder()
                .id(user.getId())
                .privileges(user.getPrivileges())
                .roles(user.getRoles())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .build();
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

}
