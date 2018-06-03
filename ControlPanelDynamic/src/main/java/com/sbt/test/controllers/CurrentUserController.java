package com.sbt.test.controllers;

import com.sbt.test.dto.OldAndNewPass;
import com.sbt.test.entities.User;
import com.sbt.test.services.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller to handle operations on current user
 */
@RestController
@CrossOrigin
@RequestMapping("/currentUser")
public class CurrentUserController extends AbstractRestController {

    private final CurrentUserService service;

    public CurrentUserController(CurrentUserService service) {
        this.service = service;
    }

    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return process(() -> service.getCurrentUser(principal.getName()));
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
