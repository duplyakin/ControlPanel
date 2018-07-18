package com.sbt.test.entities;

import com.sbt.test.hl.storage.HLEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "hlId", "values"})
@Table(name = "equipment_unit")
public class EquipmentUnit implements HLEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String hlId= UUID.randomUUID().toString();

    @OneToOne(cascade = CascadeType.MERGE)
    private EquipmentType type;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ParameterValue> values = new HashSet<>();

}
