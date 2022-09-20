package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.reborn.reborn.repository.WorkoutRepositoryTest.createMember;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @BeforeEach
    void before(){
        Member member = createMember();
        memberRepository.save(member);
    }

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("존재하는 이메일이면 true를 반환한다.")
    void existEmailTest(){
        boolean hasEmail = memberRepository.existsByEmail("email");
        Assertions.assertThat(hasEmail).isTrue();
    }
    @Test
    @DisplayName("존재하지않는 이메일이면 false를 반환한다.")
    void notExistEmailTest(){
        boolean hasEmail = memberRepository.existsByEmail("hello");
        Assertions.assertThat(hasEmail).isFalse();
    }

}