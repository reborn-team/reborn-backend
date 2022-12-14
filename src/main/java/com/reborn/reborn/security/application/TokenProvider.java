package com.reborn.reborn.security.application;

import com.reborn.reborn.member.domain.MemberRole;
import com.reborn.reborn.security.domain.AuthToken;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
public class TokenProvider {

    private final Key key;

    public TokenProvider(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, MemberRole role , Date expireDate){
        return new AuthToken(id, role, expireDate, key);
    }

    public AuthToken converterAuthToken(String token){
        return new AuthToken(token, key);
    }

}
