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
@Table(name = "equipment", indexes = {@Index(name = "name", columnList = "name")},
        uniqueConstraints = {@UniqueConstraint(name = "uniqueName", columnNames = {"name"})})
public class EquipmentType {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Parameter> parameters = new HashSet<>();


}
