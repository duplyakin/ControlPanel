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
@EqualsAndHashCode(exclude = {"id", "values"})
@Table(name = "event_unit")
public class EventUnit {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    private EventType type;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ParameterValue> values = new HashSet<>();

}