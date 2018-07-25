package com.sbt.test.controllers;

import com.sbt.test.entities.EventType;
import com.sbt.test.services.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.PostConstruct;
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

    @GetMapping("/get/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventType> get(@PathVariable String name) {
        return process(() -> service.getTypeByName(name));
    }

    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EventType>> getAll() {
        return process(service::getAllTypes);
    }


    @PutMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventType> add(@RequestBody EventType equip) {
        return process(() -> service.addType(equip));
    }

    @PostConstruct
    public void addEventType(){
        EventType t1 = new EventType();
        EventType t2 = new EventType();
        EventType t3 = new EventType();
        EventType t4 = new EventType();
        EventType t5 = new EventType();
        EventType t6 = new EventType();
        EventType t7 = new EventType();
        EventType t8 = new EventType();
        EventType t9 = new EventType();
        EventType t10= new EventType();
        EventType t11= new EventType();

        t1.setName("СПО");
        t1.setOperatingRatio(1);
        service.addType(t1);

        t2.setName("Спуск");
        t2.setOperatingRatio(0.5);
        service.addType(t2);

        t3.setName("Подъем");
        t3.setOperatingRatio(0.5);
        service.addType(t3);

        t4.setName("Бурение");
        t4.setOperatingRatio(3);
        service.addType(t4);

        t5.setName("Отбор керна");
        t5.setOperatingRatio(3);
        service.addType(t5);

        t6.setName("Проработка");
        t6.setOperatingRatio(2);
        service.addType(t6);

        t7.setName("Шаблонировка");
        t7.setOperatingRatio(2);
        service.addType(t7);

        t8.setName("Спуск обсадной колонны");
        t8.setOperatingRatio(0.5);
        service.addType(t8);

        t9.setName("Технологическое СПО");
        t9.setOperatingRatio(1);
        service.addType(t9);

        t10.setName("Аварийные работы");
        t10.setOperatingRatio(2);
        service.addType(t10);

        t11.setName("Переспуск-перетяжка");
        t11.setOperatingRatio(0);
        service.addType(t11);
    }
}