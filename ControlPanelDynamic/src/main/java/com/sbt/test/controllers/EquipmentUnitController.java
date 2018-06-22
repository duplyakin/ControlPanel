package com.sbt.test.controllers;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.services.EquipmentUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle operations on equipment
 */
@RestController
@CrossOrigin
@RequestMapping("/equipmentUnits")
public class EquipmentUnitController extends AbstractRestController {
    private final EquipmentUnitService service;

    @Autowired
    public EquipmentUnitController(EquipmentUnitService service) {
        this.service = service;
    }

    @GetMapping("get/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentUnit> get(@PathVariable long id) {
        return process(() -> service.getById(id));
    }


    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentUnit> add(@RequestBody EquipmentUnit equip) {
        return process(() -> service.addUnit(equip));
    }


}
