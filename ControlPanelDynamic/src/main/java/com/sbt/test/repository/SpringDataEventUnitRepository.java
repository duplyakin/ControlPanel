package com.sbt.test.repository;

import com.sbt.test.entities.EventUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional(Transactional.TxType.REQUIRES_NEW)
public interface SpringDataEventUnitRepository extends JpaRepository<EventUnit, Long> {

    EventUnit getById(long id);

}