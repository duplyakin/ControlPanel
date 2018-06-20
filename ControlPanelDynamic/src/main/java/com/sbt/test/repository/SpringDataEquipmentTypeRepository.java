package com.sbt.test.repository;

import com.sbt.test.entities.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataEquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {
    EquipmentType getById(long id);

    EquipmentType getByName(String name);

}
