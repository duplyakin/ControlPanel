package com.sbt.test.controllers;

import com.sbt.test.entities.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasRole('USER')")
    public User get(@PathVariable("id") int id) {
        return User.builder().username("123").build();
    }

    @PutMapping("/addUser")
    @PreAuthorize("hasRole('USER')")
    public User add(@RequestBody User user) {
        return user;
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('USER')")
    public User delete(@PathVariable("id") int id) {
        return User.builder().username("123").build();
    }


}
