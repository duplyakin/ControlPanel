package com.sbt.test.repository;

import com.sbt.test.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataEquipmentRepository extends JpaRepository<Equipment, Long> {
    Equipment getById(long id);

    Equipment getByName(String name);

}
