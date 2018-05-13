package com.sbt.test.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
public class NameWithAuthorities<T extends GrantedAuthority> {
    private final String name;
    private final Set<T> roles;
}
