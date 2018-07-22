package com.sbt.test.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "event_type", indexes = {@Index(name = "eventTypeName", columnList = "name")},
        uniqueConstraints = {@UniqueConstraint(name = "uniqueEventTypeName", columnNames = {"name"})})
public class EventType {

    @Id
    @GeneratedValue
    private long id;

    private String name;

}