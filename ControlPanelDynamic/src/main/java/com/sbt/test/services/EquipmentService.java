package com.sbt.test.services;

import com.sbt.test.entities.Equipment;
import com.sbt.test.repository.SpringDataEquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService extends AbstractUserService {
    private final SpringDataEquipmentRepository repo;

    public EquipmentService(SpringDataEquipmentRepository repo) {
        this.repo = repo;
    }

    public Equipment getByName(String name) {
        return repo.getByName(name);
    }

    public Equipment getById(long id) {
        return repo.getById(id);
    }

    public Equipment add(Equipment eq) {
        return repo.saveAndFlush(eq);
    }

    public List<Equipment> getAll() {return repo.findAll();}

}
