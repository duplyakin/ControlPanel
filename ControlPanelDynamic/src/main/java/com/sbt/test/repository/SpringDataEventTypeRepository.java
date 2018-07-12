
package com.sbt.test.repository;

import com.sbt.test.entities.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataEventTypeRepository extends JpaRepository<EventType, Long> {
    EventType getById(long id);

    EventType getByName(String name);

}