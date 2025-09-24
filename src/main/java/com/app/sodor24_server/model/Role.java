package com.app.sodor24_server.model;

import java.util.Set;

public enum Role {

    ADMIN(Set.of(Authority.READ,Authority.WRITE,Authority.EDIT,Authority.DELETE)),
    USER(Set.of(Authority.READ));
    private Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
