package com.sbt.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * maybe one day i'll make data layer prettier.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "password"})
@ToString(exclude = {"password"})
@Entity
@Table(name = "users",
        indexes = {@Index(name = "userName", columnList = "username")},
        uniqueConstraints = {@UniqueConstraint(name = "uniqueUsername", columnNames = {"username"})})
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Singular
    private Set<Role> roles;

    @ElementCollection(targetClass = Privilege.class, fetch = FetchType.EAGER)
    @Singular
    private Set<Privilege> privileges;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> result = new HashSet<>(getPrivileges());
        result.addAll(getRoles());
        return result;
    }
}
