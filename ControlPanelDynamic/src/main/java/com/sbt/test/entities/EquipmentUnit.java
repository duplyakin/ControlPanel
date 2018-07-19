package com.sbt.test.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "values"})
@Table(name = "equipment_unit")
public class EquipmentUnit {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "name")
    private EquipmentType type;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ParameterValue> values = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EventUnit> events = new ArrayList<>();

}
