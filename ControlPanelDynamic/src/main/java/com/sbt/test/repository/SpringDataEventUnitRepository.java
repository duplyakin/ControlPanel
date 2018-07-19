package com.sbt.test.repository;

import com.sbt.test.entities.EventUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataEventUnitRepository extends JpaRepository<EventUnit, Long> {
}
