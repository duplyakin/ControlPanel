package com.sbt.test.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "equipment_type", indexes = {@Index(name = "name", columnList = "name")},
        uniqueConstraints = {@UniqueConstraint(name = "uniqueName", columnNames = {"name"})})
public class EquipmentType {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Parameter> parameters = new HashSet<>();

}
