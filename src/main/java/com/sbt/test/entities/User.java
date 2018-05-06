package com.sbt.test.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users",
        indexes = {@Index(name = "userName", columnList = "username")},
        uniqueConstraints = {@UniqueConstraint(name = "uniqueUsername", columnNames = {"username"})}
)
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    private Set<Role> authorities;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;
}
