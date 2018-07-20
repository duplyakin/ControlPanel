package com.sbt.test.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, AUDITOR;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
