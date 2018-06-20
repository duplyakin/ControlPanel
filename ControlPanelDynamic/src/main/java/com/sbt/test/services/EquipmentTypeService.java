package com.sbt.test.services;

import com.sbt.test.entities.EquipmentType;
import com.sbt.test.repository.SpringDataEquipmentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentTypeService extends AbstractUserService {
    private final SpringDataEquipmentTypeRepository repo;

    public EquipmentTypeService(SpringDataEquipmentTypeRepository repo) {
        this.repo = repo;
    }

    public EquipmentType getTypeByName(String name) {
        return repo.getByName(name);
    }

    public EquipmentType addType(EquipmentType eq) {
        return repo.saveAndFlush(eq);
    }

    public List<EquipmentType> getAllTypes() {return repo.findAll();}

}
