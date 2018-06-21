package com.sbt.test.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "equipment_unit")
public class EquipmentUnit {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", unique = true, nullable = false, updatable = false)
    private EquipmentType type;

    @OneToMany(targetEntity=ParameterValue.class, mappedBy="id", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ParameterValue> values = new HashSet<>();

}
