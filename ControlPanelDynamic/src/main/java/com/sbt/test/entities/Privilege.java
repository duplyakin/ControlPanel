package com.sbt.test.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Privilege implements GrantedAuthority {
    READ, WRITE;

    @Override
    public String getAuthority() {
        return name();
    }
}