package com.sbt.test.controllers;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.entities.User;
import com.sbt.test.services.EquipmentUnitService;
import com.sbt.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller to handle operations on equipment
 */
@RestController
@CrossOrigin
@RequestMapping("/equipmentUnits")
public class EquipmentUnitController extends AbstractRestController {
    private final EquipmentUnitService service;
    private final UserService userService;

    @Autowired
    public EquipmentUnitController(EquipmentUnitService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("get/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentUnit> get(@PathVariable long id) {
        return process(() -> service.getById(id));
    }

    @GetMapping("blockchainGet/{hlId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentUnit> blockchainGet(Principal principal, @PathVariable String hlId) {
        String username = principal.getName();
        final User user = userService.get(username);
        return process(() -> service.getByhlId(user, hlId));
    }


    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentUnit> add(Principal principal, @RequestBody EquipmentUnit equip) {
        String username = principal.getName();
        final User user = userService.get(username);
        return process(() -> service.addUnit(equip, user));
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentUnit> update(Principal principal, @RequestBody EquipmentUnit equip) {
        String username = principal.getName();
        final User user = userService.get(username);
        return process(() -> service.updateUnit(equip, user));
    }

}
