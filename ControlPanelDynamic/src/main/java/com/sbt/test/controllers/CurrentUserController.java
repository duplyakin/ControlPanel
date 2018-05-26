package com.sbt.test.controllers;

import com.sbt.test.dto.OldAndNewPass;
import com.sbt.test.entities.User;
import com.sbt.test.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
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

    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> changePassword(Principal principal, @RequestBody OldAndNewPass passwords) {
        return process(() -> service.changePassword(
                principal.getName(),
                passwords.getOldPassword(),
                passwords.getNewPassword())
        );
    }
}
