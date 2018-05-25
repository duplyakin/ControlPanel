package com.sbt.test.controllers;

import com.sbt.test.entities.User;
import com.sbt.test.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CurrentUserController extends AbstractRestController {

    private final UserService service;

    public CurrentUserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/currentUser")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return process(() -> service.get(principal.getName()));
    }
}
