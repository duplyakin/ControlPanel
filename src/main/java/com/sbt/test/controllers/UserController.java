package com.sbt.test.controllers;

import com.sbt.test.entities.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User get(@PathVariable("id") int id) {
        return User.builder().name("123").build();
    }

    @PutMapping("/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public User add(@RequestBody User user) {
        return user;
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User delete(@PathVariable("id") int id) {
        return User.builder().name("123").build();
    }


}
