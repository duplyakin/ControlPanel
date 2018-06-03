package com.sbt.test.userLoader;

import com.sbt.test.annotations.MadeByGleb;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.exceptions.UserNotExistException;
import com.sbt.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@MadeByGleb
public class UserDetailsLoader implements UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public UserDetailsLoader(UserRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        repo.add(User.builder()
                .username("user")
                .password(new BCryptPasswordEncoder().encode("pass"))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(Arrays.asList(Role.ADMIN, Role.USER))
                .privileges(Arrays.asList(Privilege.READ, Privilege.WRITE))
                .build());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repo.get(username);
        } catch (UserNotExistException ex) {
            throw new UsernameNotFoundException("User " + username + " is missing");
        }
    }
}