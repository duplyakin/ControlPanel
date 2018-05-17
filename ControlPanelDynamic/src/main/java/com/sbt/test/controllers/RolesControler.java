package com.sbt.test.controllers;

import com.sbt.test.entities.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin
public class RolesControler extends AbstractRestController {

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Role>> getAllRoles() {
        return process(() -> ok(Arrays.asList(Role.values())));
    }

}
