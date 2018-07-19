package com.sbt.test.services;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.entities.EventUnit;
import com.sbt.test.repository.SpringDataEquipmentUnitRepository;
import com.sbt.test.repository.SpringDataEventUnitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventUnitService extends AbstractUserService {

    private final SpringDataEventUnitRepository repo;

    private final SpringDataEquipmentUnitRepository unitRepository;

    public EventUnitService(SpringDataEventUnitRepository repo, SpringDataEquipmentUnitRepository unitRepository) {
        this.repo = repo;
        this.unitRepository = unitRepository;
    }

    public EventUnit addEvent(long parentId, EventUnit unit) {
        return supplyUser(() -> {
            EquipmentUnit equipment = unitRepository.getOne(parentId);
            List<EventUnit> events = new ArrayList<>(equipment.getEvents());
            events.add(unit);
            equipment.setEvents(events);
            return repo.saveAndFlush(unit);
        });
    }

    public EventUnit getEventUnitById(long id) {
        return supplyUser(() -> repo.getOne(id));
    }

}
