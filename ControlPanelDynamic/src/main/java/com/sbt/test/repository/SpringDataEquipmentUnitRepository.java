package com.sbt.test.repository;

import com.sbt.test.entities.EquipmentUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataEquipmentUnitRepository extends JpaRepository<EquipmentUnit, Long> {

    EquipmentUnit getById(long id);

}
