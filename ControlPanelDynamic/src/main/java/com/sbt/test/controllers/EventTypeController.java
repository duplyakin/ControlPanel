package com.sbt.test.controllers;

import com.sbt.test.entities.EventType;
import com.sbt.test.services.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to handle operations on event
 */
@RestController
@CrossOrigin
@RequestMapping("/eventTypes")
public class EventTypeController extends AbstractRestController {
    private final EventTypeService service;

    @Autowired
    public EventTypeController(EventTypeService service) {
        this.service = service;
    }

    @GetMapping("get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventType> get(@PathVariable String name) {
        return process(() -> service.getTypeByName(name));
    }

    @GetMapping("getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EventType>> getAll() {
        return process(service::getAllTypes);
    }


    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventType> add(@RequestBody EventType equip) {
        return process(() -> service.addType(equip));
    }


}