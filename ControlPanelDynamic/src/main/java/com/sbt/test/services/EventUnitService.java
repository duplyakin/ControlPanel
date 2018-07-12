
package com.sbt.test.services;

import com.sbt.test.entities.EventUnit;
import com.sbt.test.repository.SpringDataEventUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class EventUnitService extends AbstractUserService {

    private final SpringDataEventUnitRepository repo;

    public EventUnitService(SpringDataEventUnitRepository repo) {
        this.repo = repo;
    }

    public EventUnit addUnit(EventUnit eq) {
        return repo.saveAndFlush(eq);
    }

    public EventUnit getById(long id) {
        return repo.getById(id);
    }

}