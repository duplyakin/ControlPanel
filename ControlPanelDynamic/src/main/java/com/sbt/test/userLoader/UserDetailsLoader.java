package com.sbt.test.userLoader;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import java.util.Collections;

@Service
public class UserDetailsLoader implements UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public UserDetailsLoader(UserRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        repo.update(User.builder()
                .username("user")
                .password(new BCryptPasswordEncoder().encode("pass"))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(Collections.singleton(Role.USER))
                .privileges(Collections.singleton(Privilege.READ))
                .build());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repo.get(username);
        } catch (PersistenceException pex) {
            throw new UsernameNotFoundException("User " + username + " is missing");
        }
    }
}