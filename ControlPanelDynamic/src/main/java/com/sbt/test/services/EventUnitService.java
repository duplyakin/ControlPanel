package com.sbt.test.services;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.entities.EventUnit;
import com.sbt.test.entities.User;
import com.sbt.test.repository.SpringDataEquipmentUnitRepository;
import com.sbt.test.repository.EventUnitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventUnitService extends AbstractUserService {

    private final EventUnitRepository repo;

    private final SpringDataEquipmentUnitRepository unitRepository;

    public EventUnitService(EventUnitRepository repo, SpringDataEquipmentUnitRepository unitRepository) {
        this.repo = repo;
        this.unitRepository = unitRepository;
    }

    public EventUnit addEvent(long parentId, EventUnit unit, User user) {
        return supplyUser(() -> {
            EquipmentUnit equipment = unitRepository.getOne(parentId);
            List<EventUnit> events = new ArrayList<>(equipment.getEvents());
            events.add(unit);
            equipment.setEvents(events);
            return repo.addToHl(unit,user);
        });
    }

    public EventUnit getEventUnitById(long id) {
        return supplyUser(() -> repo.get(id));
    }

}
