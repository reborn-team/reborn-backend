package com.reborn.reborn.repository;

import com.reborn.reborn.dto.MemberUpdateRequest;
import com.reborn.reborn.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.reborn.reborn.repository.WorkoutRepositoryTest.createMember;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void before() {
        Member member = createMember();
        memberRepository.save(member);
    }

    @Test
    @DisplayName("존재하는 이메일이면 true를 반환한다.")
    void existEmailTest() {
        boolean hasEmail = memberRepository.existsByEmail("email");
        assertThat(hasEmail).isTrue();
    }

    @Test
    @DisplayName("존재하지않는 이메일이면 false를 반환한다.")
    void notExistEmailTest() {
        boolean hasEmail = memberRepository.existsByEmail("hello");
        assertThat(hasEmail).isFalse();
    }

    @Test
    @DisplayName("회원정보를 수정한다")
    void modifyMember() {
        Member member = createMember();
        memberRepository.save(member);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();
        MemberUpdateRequest request = new MemberUpdateRequest("update", "010", "zip", "road", "detail");

        Member requestMember = request.toEntity(request);
        findMember.modifyInfo(requestMember);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(member.getId()).get();
        assertThat(member1.getNickname()).isEqualTo(request.getNickname());


    }
}