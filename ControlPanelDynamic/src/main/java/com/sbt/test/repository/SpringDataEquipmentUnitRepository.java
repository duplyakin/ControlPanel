package com.sbt.test.repository;

import com.sbt.test.entities.EquipmentUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional(Transactional.TxType.REQUIRES_NEW)
public interface SpringDataEquipmentUnitRepository extends JpaRepository<EquipmentUnit, Long> {

    EquipmentUnit getById(long id);

}
