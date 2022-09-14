package com.reborn.reborn.controller;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.security.jwt.AuthToken;
import com.reborn.reborn.security.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.util.Date;
@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs
@SpringBootTest // -> apply(documentationConfiguration(restDocumentation))
public class ControllerConfig {

    private TokenProvider tokenProvider;
    private AuthToken token;
    @Autowired
    private MemberRepository memberRepository;
    @Value("${spring.jwt.secret-key}")
    private String value;

    @BeforeEach
    void before() {
        tokenProvider = new TokenProvider(value);
        token = tokenProvider.createAuthToken("user", MemberRole.USER, new Date(100000000000L));

    }

    @PostConstruct
    void createWorkout() {
        memberRepository.deleteAll();
        Member member = Member.builder().email("email@naver.com")
                .name("han").build();
        memberRepository.save(member);

    }
    public String getToken(Member member) {
        return token.createToken(member.getEmail(), member.getMemberRole(), new Date());
    }
}
