package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class RepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void memberRepositoryTest() throws Exception{
        //given
        memberRepository.save(Member.builder().email("reborn11@naver.com")
                .password(passwordEncoder.encode("1")).memberRole(MemberRole.USER).build());

        //when

        //then
    }
}
