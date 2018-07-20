package com.sbt.test.repository;

import com.sbt.test.entities.EventUnit;
import com.sbt.test.hl.HLProvider;
import com.sbt.test.hl.storage.AbstractHlConstEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class EventUnitRepositoryProxy extends AbstractHlConstEntityRepository<EventUnit> implements EventUnitRepository {
    @Autowired
    public EventUnitRepositoryProxy(JpaRepository<EventUnit, Long> repo, HLProvider hlProvider) {
        super(repo, hlProvider, EventUnit.class);
    }
}
