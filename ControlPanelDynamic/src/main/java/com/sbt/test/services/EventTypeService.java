package com.sbt.test.services;

import com.sbt.test.entities.EventType;
import com.sbt.test.repository.SpringDataEventTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService extends AbstractUserService {
    private final SpringDataEventTypeRepository repo;

    public EventTypeService(SpringDataEventTypeRepository repo) {
        this.repo = repo;
    }

    public EventType getTypeByName(String name) {
        return repo.getByName(name);
    }

    public EventType addType(EventType eq) {
        return repo.saveAndFlush(eq);
    }

    public List<EventType> getAllTypes() {
        return repo.findAll();
    }

}