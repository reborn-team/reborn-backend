package com.reborn.reborn.security.domain;

import com.reborn.reborn.security.application.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${spring.jwt.secret-key}")
    private String secret;

    @Bean
    public TokenProvider jwtProvider(){
        return new TokenProvider(secret);
    }

}
