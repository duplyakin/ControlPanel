package com.sbt.test.controllers;

import com.sbt.test.entities.EquipmentType;
import com.sbt.test.entities.Parameter;
import com.sbt.test.services.EquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @PostConstruct
    public void addEventType(){
        Parameter p1 = new Parameter();
        Parameter p2 = new Parameter();
        Parameter p3 = new Parameter();
        Parameter p4 = new Parameter();
        Parameter p5 = new Parameter();
        Parameter p6 = new Parameter();

        EquipmentType et = new EquipmentType();

        p1.setName("Вес талевого блока, т");
        p2.setName("Длина свечи, м");
        p3.setName("Предыдущая наработка, т*км");
        p4.setName("№ бухты/сертификата");
        p5.setName("D каната, мм");
        p6.setName("Каната в бухте, м");

        Set<Parameter> parSet = new HashSet<Parameter>();
        parSet.add(p1);
        parSet.add(p2);
        parSet.add(p3);
        parSet.add(p4);
        parSet.add(p5);
        parSet.add(p6);


        et.setParameters(parSet);
        et.setName("Талевый канат");
        service.addType(et);
    }

}
