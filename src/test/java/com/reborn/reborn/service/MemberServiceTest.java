package com.reborn.reborn.service;

import com.reborn.reborn.dto.ChangePasswordDto;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
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

    @Test
    @DisplayName("요청된 비밀번호가 현재 비밀번호와 같으면 비밀번호를 변경한다.")
    void changePasswordTest() {
        Member member = Member.builder().password("1").build();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("1", "a");

        String rawPassword = member.getPassword();

        given(passwordEncoder.matches(rawPassword, changePasswordDto.getRawPassword())).willReturn(true);


        memberService.updatePassword(member, changePasswordDto);

        assertThat(member.getPassword()).isNotEqualTo(rawPassword);
    }

    @Test
    @DisplayName("요청된 비밀번호가 현재 비밀번호와 다르면 예외를 반환한다.")
    void changePasswordExceptionTest() {
        Member member = Member.builder().password("1").build();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("1", "a");

        String rawPassword = member.getPassword();

        given(passwordEncoder.matches(rawPassword, changePasswordDto.getRawPassword())).willReturn(false);


        assertThatThrownBy(()->memberService.updatePassword(member, changePasswordDto)).isInstanceOf(IllegalStateException.class);
    }
}
