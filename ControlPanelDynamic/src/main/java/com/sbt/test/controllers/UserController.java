package com.sbt.test.controllers;

import com.sbt.test.entities.User;
import com.sbt.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController extends AbstractRestController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/get/{username}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')  and hasAnyAuthority('READ', 'WRITE')")
    public ResponseEntity<User> get(@PathVariable("username") String username) {
        return process(() -> service.get(username));
    }

    @PutMapping("/add")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('WRITE')")
    public ResponseEntity<User> add(@RequestBody User user) {
        return process(() -> service.add(user));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('WRITE')")
    public ResponseEntity<User> update(@RequestBody User user) {
        return process(() -> service.update(user));
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('WRITE')")
    public ResponseEntity<User> delete(@PathVariable("username") String username) {
        return process(() -> service.delete(username));
    }
}
