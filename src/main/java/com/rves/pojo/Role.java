package com.rves.pojo;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    ROLE_SUPER ,
    ROLE_HOTEL_ADMIN,
    ROLE_HOTEL_CLEANER;

    @Override
    public String getAuthority() {
        return name();
    }
}
