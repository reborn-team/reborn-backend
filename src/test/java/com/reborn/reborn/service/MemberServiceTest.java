package com.reborn.reborn.service;

import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("회원 가입하고 Id 값을 리턴한다.")
    void create() {
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .password("A").build();
        Member member = Member.builder().build();

        given(memberRepository.save(any())).willReturn(member);

        Long memberId = memberService.registerMember(requestDto);

        verify(memberRepository).save(any());
        Assertions.assertThat(memberId).isEqualTo(member.getId());


    }

    @Test
    @DisplayName("해당 이메일이 존재하면 True를 반환한다.")
    void emailCheckTest() {
        Member member = Member.builder()
                .name("han")
                .password("a")
                .email("reborn@naver.com").build();
        given(memberRepository.existsByEmail(member.getEmail())).willReturn(true);

        boolean value = memberService.emailDuplicateCheck("reborn@naver.com");

        assertThat(value).isEqualTo(true);


    }


}
