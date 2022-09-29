package com.reborn.reborn.member.domain;

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
