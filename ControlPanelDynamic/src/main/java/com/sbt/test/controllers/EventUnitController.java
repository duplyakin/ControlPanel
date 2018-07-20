package com.sbt.test.controllers;

import com.sbt.test.entities.EventUnit;
import com.sbt.test.entities.User;
import com.sbt.test.services.EventUnitService;
import com.sbt.test.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/events")
public class EventUnitController extends AbstractRestController {

    private final EventUnitService service;
    private final UserService userService;

    public EventUnitController(EventUnitService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventUnit> get(@RequestParam long id) {
        return process(() -> service.getEventUnitById(id));
    }

    @PutMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventUnit> add(Principal principal, @RequestBody EventUnit unit, @PathVariable long id) {
        String username =principal.getName();
        final User user=userService.get(username);
        return process(() -> service.addEvent(id, unit,user));
    }

}
