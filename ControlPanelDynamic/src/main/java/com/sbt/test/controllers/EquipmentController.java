package com.sbt.test.controllers;

import com.sbt.test.entities.Equipment;
import com.sbt.test.services.EquipmentService;
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
@RequestMapping("/equipment")
public class EquipmentController extends AbstractRestController {
    private final EquipmentService service;

    @Autowired
    public EquipmentController(EquipmentService service) {
        this.service = service;
    }

    @GetMapping("get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Equipment> get(@PathVariable String name) {
        return process(() -> service.getByName(name));
    }

    @GetMapping("getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Equipment>> getAll() {
        return process(service::getAll);
    }


    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Equipment> add(@RequestBody Equipment equip) {
        return process(() -> service.add(equip));
    }


}
