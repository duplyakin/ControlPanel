package com.sbt.test.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Privilege implements GrantedAuthority {
    READ, WRITE, APPROVE;

    @Override
    public String getAuthority() {
        return name();
    }
}