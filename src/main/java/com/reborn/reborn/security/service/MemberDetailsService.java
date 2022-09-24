package com.reborn.reborn.security.service;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByEmail(username);
        log.info("MemberDetailsService");
        if (findMember.isEmpty()) {
            log.info("aaaaa");
            throw new UsernameNotFoundException("email 확인하세요");
        }

        return UserPrincipal.create(findMember.get());
    }
}
