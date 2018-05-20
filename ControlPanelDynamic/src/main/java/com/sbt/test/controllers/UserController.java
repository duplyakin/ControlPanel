package com.sbt.test.controllers;

import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> get(@PathVariable("username") String username) {
        return process(() -> service.get(username));
    }

    @PutMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> add(@RequestBody User user) {
        return process(() -> service.add(user));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> update(@RequestBody User user) {
        return process(() -> service.update(user));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> delete(@RequestAttribute("username") String username) {
        return process(() -> service.delete(username));
    }

    @PostMapping("/setRoles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setRoles(@RequestBody NameWithAuthorities<Role> usernameAndRoles) {
        return process(() -> service.setRoles(usernameAndRoles.getName(), usernameAndRoles.getAuthorities()));
    }

    @PostMapping("/setPrivileges")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setPrivileges(@RequestBody NameWithAuthorities<Privilege> usernameAndPrivileges) {
        return process(() -> service.setPrivileges(usernameAndPrivileges.getName(), usernameAndPrivileges.getAuthorities()));
    }
}
