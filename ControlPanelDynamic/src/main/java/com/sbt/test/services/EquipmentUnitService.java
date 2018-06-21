package com.sbt.test.services;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.repository.SpringDataEquipmentUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class EquipmentUnitService extends AbstractUserService {

    private final SpringDataEquipmentUnitRepository repo;

    public EquipmentUnitService(SpringDataEquipmentUnitRepository repo) {
        this.repo = repo;
    }

    public EquipmentUnit addUnit(EquipmentUnit eq) {
        return repo.saveAndFlush(eq);
    }

    public EquipmentUnit getById(long id) {
        return repo.getById(id);
    }

}
