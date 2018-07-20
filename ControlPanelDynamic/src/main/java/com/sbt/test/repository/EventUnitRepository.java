package com.sbt.test.repository;

import com.sbt.test.entities.EventUnit;
import com.sbt.test.hl.storage.HLConstEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EventUnitRepository extends HLConstEntityRepository<EventUnit> {
}
