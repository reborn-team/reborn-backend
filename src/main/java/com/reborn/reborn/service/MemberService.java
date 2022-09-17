package com.reborn.reborn.service;

import com.reborn.reborn.dto.ChangePasswordDto;
import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long registerMember(MemberRequestDto memberRequestDto) {
        Member member = memberRequestDto.toEntity(memberRequestDto);
        member.changePassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member save = memberRepository.save(member);
        return save.getId();
    }

    public boolean emailDuplicateCheck(String email) {
        return memberRepository.existsByEmail(email);

    }

    @Transactional
    public void updatePassword(Member member, ChangePasswordDto request) {
        if (isNotMatchRawPassword(member, request)) {
            throw new IllegalStateException("Password가 맞지 않습니다.");
        }
        member.changePassword(passwordEncoder.encode(request.getChangePassword()));
        memberRepository.save(member);
    }

    private boolean isNotMatchRawPassword(Member member, ChangePasswordDto request) {
        return !passwordEncoder.matches(request.getRawPassword(), member.getPassword());
    }


}
