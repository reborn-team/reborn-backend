package com.reborn.reborn.service;

import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.entity.Address;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long registerMember(MemberRequestDto memberRequestDto) {
        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .phone(memberRequestDto.getPhoneNum())
                .email(memberRequestDto.getEmail())
                .address(new Address(memberRequestDto.getAddress(), memberRequestDto.getDetailAddress(), memberRequestDto.getPostcode()))
                .memberRole(MemberRole.USER)
                .build();
        Member save = memberRepository.save(member);
        return save.getId();
    }
        //TODO 테스트용 지워야
    @Override
    public boolean emailDuplicateCheck(String email) {
        return  memberRepository.existsByEmail(email);

    }
}
