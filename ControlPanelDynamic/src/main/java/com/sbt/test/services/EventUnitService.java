package com.sbt.test.services;

import com.sbt.test.entities.EquipmentUnit;
import com.sbt.test.entities.EventUnit;
import com.sbt.test.entities.User;
import com.sbt.test.repository.EquipmentUnitRepository;
import com.sbt.test.repository.EventUnitRepository;
import com.sbt.test.repository.SpringDataEventTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventUnitService extends AbstractUserService {

    private final EventUnitRepository repo;

    private final SpringDataEventTypeRepository typeRepo;

    private final EquipmentUnitRepository unitRepository;

    public EventUnitService(EventUnitRepository repo, SpringDataEventTypeRepository typeRepo, EquipmentUnitRepository unitRepository) {
        this.repo = repo;
        this.typeRepo = typeRepo;
        this.unitRepository = unitRepository;
    }

    public EventUnit addEvent(long parentId, EventUnit unit, User user) {
        return supplyUser(() -> {
            EquipmentUnit equipment = unitRepository.getById(parentId);
            unit.setType(typeRepo.getByName(unit.getType().getName()));
            List<EventUnit> events = new ArrayList<>(equipment.getEvents());
            events.add(unit);
            equipment.setEvents(events);
            return repo.addToHl(unit, user);
        });
    }

    public EventUnit getEventUnitById(long id) {
        return supplyUser(() -> repo.get(id));
    }

    public EventUnit getByhlId(User user, String hlId) {
        return repo.getFromHl(hlId, user);
    }
}
