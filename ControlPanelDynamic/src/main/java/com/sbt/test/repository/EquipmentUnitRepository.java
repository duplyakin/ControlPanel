package com.sbt.test.repository;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.hl.storage.HLConstEntityRepository;

import java.util.Collection;

public interface EquipmentUnitRepository extends HLConstEntityRepository<EquipmentUnit> {

    EquipmentUnit saveAndFlush(EquipmentUnit in);

    EquipmentUnit getById(long id);

    EquipmentUnit updateUnit(EquipmentUnit unit);

    Collection<EquipmentUnit> getAll();
}
