package com.rves.pojo;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    ROLE_ADMIN ,
    ROLE_USER,
    ROLE_CLEANER;

    @Override
    public String getAuthority() {
        return name();
    }
}
