package com.reborn.reborn.member.application;

import com.reborn.reborn.member.presentation.dto.ChangePasswordRequest;
import com.reborn.reborn.member.presentation.dto.MemberRequest;
import com.reborn.reborn.member.presentation.dto.MemberResponse;
import com.reborn.reborn.member.presentation.dto.MemberEditForm;
import com.reborn.reborn.member.domain.Address;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

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
        MemberRequest requestDto = MemberRequest.builder()
                .password("A").build();
        Member member = Member.builder().build();

        given(memberRepository.save(any())).willReturn(member);

        Long memberId = memberService.registerMember(requestDto);

        verify(memberRepository).save(any());
        assertThat(memberId).isEqualTo(member.getId());


    }

    @Test
    @DisplayName("해당 이메일이 존재하면 True를 반환한다.")
    void emailCheckTest() {
        Member member = Member.builder()
                .nickname("han")
                .password("a")
                .email("reborn@naver.com").build();
        given(memberRepository.existsByEmail(member.getEmail())).willReturn(true);

        boolean value = memberService.emailDuplicateCheck("reborn@naver.com");

        assertThat(value).isEqualTo(true);

    }

    @Test
    @DisplayName("해당 닉네임이 존재하면 True를 반환한다.")
    void nicknameCheckTest() {
        Member member = Member.builder()
                .nickname("han")
                .password("a")
                .email("reborn@naver.com").build();
        given(memberRepository.existsByNickname(member.getNickname())).willReturn(true);

        boolean value = memberService.nicknameDuplicateCheck("han");

        assertThat(value).isEqualTo(true);

    }

    @Test
    @DisplayName("요청된 비밀번호가 현재 비밀번호와 같으면 비밀번호를 변경한다.")
    void changePasswordTest() {
        Member member = Member.builder().password("1").build();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("1", "a");

        String rawPassword = member.getPassword();

        given(passwordEncoder.matches(rawPassword, changePasswordRequest.getRawPassword())).willReturn(true);
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

        memberService.changePassword(member.getId(), changePasswordRequest);

        assertThat(member.getPassword()).isNotEqualTo(rawPassword);
    }

    @Test
    @DisplayName("요청된 비밀번호가 현재 비밀번호와 다르면 예외를 반환한다.")
    void changePasswordExceptionTest() {
        Member member = Member.builder().password("1").build();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("1", "a");

        String rawPassword = member.getPassword();

        given(passwordEncoder.matches(rawPassword, changePasswordRequest.getRawPassword())).willReturn(false);
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));


        assertThatThrownBy(() -> memberService.changePassword(member.getId(), changePasswordRequest)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("요청된 정보로 회원을 수정한다")
    void modifyMember() {
        Member member = Member.builder().nickname("nick").phone("2").password("1").build();
        MemberEditForm request = new MemberEditForm("name", "1", "zipcode", "road", "detail");

        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        memberService.updateMember(member.getId(), request);

        assertThat(member.getNickname()).isEqualTo(request.getNickname());
    }

    @Test
    @DisplayName("회원 정보를 조회하여 Dto로 반환한다.")
    void getOne() {
        Member member = Member.builder().nickname("nick").phone("2").password("1").address(new Address("road", "detail", "zip")).build();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        MemberResponse response = memberService.getOne(member.getId());

        assertThat(member.getNickname()).isEqualTo(response.getNickname());

    }
}
