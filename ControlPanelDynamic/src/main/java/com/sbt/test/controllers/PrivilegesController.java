package com.sbt.test.controllers;

import com.sbt.test.dto.NameWithAuthorities;
import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.User;
import com.sbt.test.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/privileges")
@CrossOrigin
public class PrivilegesController extends AbstractRestController {

    private final AuthorityService service;

    @Autowired
    public PrivilegesController(AuthorityService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        return process(() -> Arrays.asList(Privilege.values()));
    }

    @PostMapping("/set")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('WRITE')")
    public ResponseEntity<User> setPrivileges(@RequestBody NameWithAuthorities<Privilege> usernameAndPrivileges) {
        return process(() -> service.setPrivileges(usernameAndPrivileges.getName(), usernameAndPrivileges.getAuthorities()));
    }

}
