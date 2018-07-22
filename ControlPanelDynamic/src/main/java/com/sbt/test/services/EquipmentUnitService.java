package com.sbt.test.services;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.entities.ParameterValue;
import com.sbt.test.entities.User;
import com.sbt.test.repository.EquipmentUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class EquipmentUnitService extends AbstractUserService {

    // private final HLProvider provider;

    private final EquipmentUnitRepository repo;

    @Autowired
    public EquipmentUnitService(/*HLProvider provider, */EquipmentUnitRepository repo) {
        //  this.provider = provider;
        this.repo = repo;
    }


    public EquipmentUnit addUnit(EquipmentUnit eq, User user) {
        EquipmentUnit unit = repo.addToHl(eq, user);
        return unit;
    }


    //ToDo: написать нормально
    public EquipmentUnit updateUnit(EquipmentUnit eq, User user) {
        if (eq == null) {
            return eq;
        }
        if (Long.compare(eq.getId(), 0L) == 0) {
            return repo.updateUnit(eq);
        }
        EquipmentUnit unit = repo.getById(eq.getId());
        if (unit == null) {
            return repo.saveAndFlush(eq);
        }
        Set<ParameterValue> persisted = unit.getValues();
        Set<ParameterValue> existing = eq.getValues();
        for (ParameterValue vEx : existing) {
            for (ParameterValue vPers : persisted) {
                if (vPers.getParameter().equals(vEx.getParameter())) {
                    vPers.setValue(vEx.getValue());
                }
            }
        }
        return repo.addToHl(unit, user);
    }

    public EquipmentUnit getById(long id) {
        return repo.getById(id);
    }

    public EquipmentUnit getByhlId(User user, String hlId) {
        return repo.getFromHl(hlId, user);
    }
}
