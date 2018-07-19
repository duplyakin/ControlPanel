package com.sbt.test.controllers;

import com.sbt.test.entities.EventUnit;
import com.sbt.test.services.EventUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/events")
public class EventUnitController extends AbstractRestController {

    private final EventUnitService service;

    public EventUnitController(EventUnitService service) {
        this.service = service;
    }

    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventUnit> get(@RequestParam long id) {
        return process(() -> service.getEventUnitById(id));
    }

    @PutMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventUnit> add(@RequestBody EventUnit unit, @PathVariable long id) {
        return process(() -> service.addEvent(id, unit));
    }

}
