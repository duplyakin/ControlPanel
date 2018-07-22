package com.sbt.test.repository;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.hl.HLProvider;
import com.sbt.test.hl.storage.AbstractHlConstEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@Slf4j
public class EquipmentUnitRepositoryProxy extends AbstractHlConstEntityRepository<EquipmentUnit> implements EquipmentUnitRepository {

    @Autowired
    public EquipmentUnitRepositoryProxy(JpaRepository<EquipmentUnit, Long> repo, HLProvider hlProvider) {
        super(repo, hlProvider, EquipmentUnit.class);
    }


    @Override
    public EquipmentUnit saveAndFlush(EquipmentUnit in) {
        return getJpaRepository().saveAndFlush(in);
    }

    @Override
    public EquipmentUnit getById(long id) {
        EquipmentUnit equ = getJpaRepository().findById(id).get();
        return equ;
    }

    @Override
    public EquipmentUnit updateUnit(EquipmentUnit unit) {
        return getJpaRepository().saveAndFlush(unit);
    }

    @Override
    public Collection<EquipmentUnit> getAll() {
        return getJpaRepository().findAll();
    }
}
