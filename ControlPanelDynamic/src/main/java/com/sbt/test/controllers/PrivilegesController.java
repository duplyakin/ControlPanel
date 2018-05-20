package com.sbt.test.controllers;

import com.sbt.test.entities.Privilege;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/privileges")
@CrossOrigin
public class PrivilegesController extends AbstractRestController {

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        return process(() -> Arrays.asList(Privilege.values()));
    }

}
