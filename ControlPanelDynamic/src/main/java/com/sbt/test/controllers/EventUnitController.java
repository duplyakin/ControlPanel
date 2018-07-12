
package com.sbt.test.controllers;

import com.sbt.test.entities.EventUnit;
import com.sbt.test.services.EventUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle operations on event
 */
@RestController
@CrossOrigin
@RequestMapping("/eventUnits")
public class EventUnitController extends AbstractRestController {
    private final EventUnitService service;

    @Autowired
    public EventUnitController(EventUnitService service) {
        this.service = service;
    }

    @GetMapping("get/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventUnit> get(@PathVariable long id) {
        return process(() -> service.getById(id));
    }


    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventUnit> add(@RequestBody EventUnit equip) {
        return process(() -> service.addUnit(equip));
    }


}