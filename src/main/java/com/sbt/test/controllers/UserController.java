package com.sbt.test.controllers;

import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserRepository repo;

    @Autowired
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/get/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> get(@PathVariable("username") String username) {
        try {
            return repo.getByUsername(username)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).build());
        } catch (RuntimeException e) {
            log.error("Something really bad happened on getUser operation:", e);
            return ResponseEntity.status(412).build();
        }
    }

    @PutMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            return ResponseEntity.ok(repo.saveAndFlush(user));
        } catch (RuntimeException e) {
            log.error("Something really bad happened on addUser operation:", e);
            return ResponseEntity.status(412).build();
        }
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> delete(@PathVariable("username") String username) {
        repo.deleteByUsername(username);
        return ResponseEntity.ok(null);
    }


}
