package com.sbt.test.controllers;

import com.sbt.test.entities.EquipmentType;
import com.sbt.test.services.EquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to handle operations on equipment
 */
@RestController
@CrossOrigin
@RequestMapping("/equipmentTypes")
public class EquipmentTypeController extends AbstractRestController {
    private final EquipmentTypeService service;

    @Autowired
    public EquipmentTypeController(EquipmentTypeService service) {
        this.service = service;
    }

    @GetMapping("get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentType> get(@PathVariable String name) {
        return process(() -> service.getTypeByName(name));
    }

    @GetMapping("getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EquipmentType>> getAll() {
        return process(service::getAllTypes);
    }


    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EquipmentType> add(@RequestBody EquipmentType equip) {
        return process(() -> service.addType(equip));
    }


}
