package com.sbt.test.entities;

import com.sbt.test.hl.storage.HLEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "event_unit")
public class EventUnit implements HLEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String hlId= UUID.randomUUID().toString();

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