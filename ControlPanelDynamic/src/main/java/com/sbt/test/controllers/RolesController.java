package com.sbt.test.controllers;

import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin
public class RolesController extends AbstractRestController {

    private final AuthorityService service;

    @Autowired
    public RolesController(AuthorityService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Role>> getAllRoles() {
        return process(() -> Arrays.asList(Role.values()));
    }

    @PostMapping("/set")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('WRITE')")
    public ResponseEntity<User> setRoles(@RequestBody NameWithAuthorities<Role> usernameAndRoles) {
        return process(() -> service.setRoles(usernameAndRoles.getName(), usernameAndRoles.getAuthorities()));
    }

}
