package com.reborn.reborn.member.application;

import com.reborn.reborn.member.exception.MemberNotFoundException;
import com.reborn.reborn.member.exception.PasswordNotMatchException;
import com.reborn.reborn.member.presentation.dto.ChangePasswordRequest;
import com.reborn.reborn.member.presentation.dto.MemberRequest;
import com.reborn.reborn.member.presentation.dto.MemberResponse;
import com.reborn.reborn.member.presentation.dto.MemberEditForm;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Long registerMember(MemberRequest memberRequest) {
        Member member = MemberRequest.toEntity(memberRequest);
        member.changePassword(passwordEncoder.encode(memberRequest.getPassword()));
        Member save = memberRepository.save(member);
        return save.getId();
    }

    @Transactional(readOnly = true)
    public boolean emailDuplicateCheck(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean nicknameDuplicateCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public void changePassword(Long memberId, ChangePasswordRequest request) {
        Member member = getMember(memberId);
        if (isNotMatchRawPassword(member, request)) {
            throw new PasswordNotMatchException();
        }
        member.changePassword(passwordEncoder.encode(request.getChangePassword()));
    }

    public void updateMember(Long memberId, MemberEditForm request) {
        Member member = getMember(memberId);
        Member data = MemberEditForm.of(request);
        member.modifyInfo(data);
    }

    public MemberResponse getOne(Long memberId) {
        Member member = getMember(memberId);
        return MemberResponse.of(member);
    }

    public void deleteMember(Long memberId) {
        memberRepository.delete(getMember(memberId));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId.toString()));
    }

    private boolean isNotMatchRawPassword(Member member, ChangePasswordRequest request) {
        return !passwordEncoder.matches(request.getRawPassword(), member.getPassword());
    }
}
