package com.reborn.reborn.member.domain.repository;

import com.reborn.reborn.member.domain.MemberRole;
import com.reborn.reborn.member.presentation.dto.MemberEditForm;
import com.reborn.reborn.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.reborn.reborn.config.ControllerConfig.getMember;
import static org.assertj.core.api.Assertions.*;

@Slf4j
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
        Member member = getMember();
        memberRepository.save(member);
        em.flush();
        em.clear();
        log.info("repository ={}", memberRepository.getClass());
        Member findMember = memberRepository.findById(member.getId()).get();
        MemberEditForm request = new MemberEditForm("update", "010", "zip", "road", "detail");

        Member requestMember = request.of(request);
        findMember.modifyInfo(requestMember);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(member.getId()).get();
        assertThat(member1.getNickname()).isEqualTo(request.getNickname());


    }
    public  Member createMember() {
        Member member = Member.builder()
                .email("email")
                .memberRole(MemberRole.USER)
                .password("A")
                .nickname("han")
                .build();
        return member;
    }

}