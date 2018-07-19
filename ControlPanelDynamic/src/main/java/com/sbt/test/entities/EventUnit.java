package com.sbt.test.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "event_unit")
public class EventUnit {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "name")
    private EventType type;

    private Date operationDateTime;

    private int startDepthInMeters;

    private int endDepthInMeters;

    private long startMaxWeightKilos;

    private long endMaxWeightKilos;

    private int perespuskInMeters;

    private String place;


}