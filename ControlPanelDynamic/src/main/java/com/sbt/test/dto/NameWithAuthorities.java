package com.sbt.test.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * made because unmarshalled request body is expected to contain only 1 object.
 */
@Data
public class NameWithAuthorities<T extends GrantedAuthority> {
    private final String name;
    private final Set<T> authorities;
}
