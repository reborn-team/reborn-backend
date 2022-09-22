package com.reborn.reborn.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String key;

    public String getKey() {
        return key;
    }
}
