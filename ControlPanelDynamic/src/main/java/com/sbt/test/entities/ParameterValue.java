package com.sbt.test.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Table(name = "parameter_value")
public class ParameterValue {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "id", unique = true, nullable = false, updatable = false)
    private Parameter parameter;

    private String value;

}
