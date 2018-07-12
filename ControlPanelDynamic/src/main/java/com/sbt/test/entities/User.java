package com.sbt.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbt.test.hl.HLEnrollment;
import lombok.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;

/**
 * maybe one day i'll make data layer prettier.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "password"})
@ToString(exclude = {"password"})
@Entity
@Table(name = "users",
        indexes = {@Index(name = "userName", columnList = "username")},
        uniqueConstraints = {@UniqueConstraint(name = "uniqueUsername", columnNames = {"username"})})
public class User implements UserDetails, Serializable,org.hyperledger.fabric.sdk.User {

    public User (){
        enrollment=null;
    }


    @Id
    @GeneratedValue
    private long id;

    @JsonProperty("roles")
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Singular
    private Set<Role> springRoles;

    @ElementCollection(targetClass = Privilege.class, fetch = FetchType.EAGER)
    @Singular
    private Set<Privilege> privileges;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private String affiliation;

    private String mspId;

    private String account;

    @OneToOne(optional = true, targetEntity = HLEnrollment.class, cascade = ALL)
    Enrollment enrollment;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> result = new HashSet<>(getPrivileges());
        result.addAll(springRoles);
        return result;
    }
    @JsonIgnore
    @Override
    public String getName() {
        return username;
    }

    @JsonIgnore
    @Override
    public Set<String> getRoles() {
        Set<String> result = new HashSet<>();
        springRoles.forEach(role -> result.add(role.name()));
        return result;
    }

    @JsonIgnore
    @Override
    public Enrollment getEnrollment() {
        return enrollment;
    }
}
