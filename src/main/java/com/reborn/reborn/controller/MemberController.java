package com.reborn.reborn.controller;

import com.reborn.reborn.dto.*;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<JoinResponseDto> join(@RequestBody MemberRequestDto memberRequestDto) {
        Long memberId = memberService.registerMember(memberRequestDto);
        log.info("aa");
        return ResponseEntity.created(URI.create("/api/v1/members/" + memberId)).body(new JoinResponseDto(memberId));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> modify(@LoginMember Long memberId, @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(memberId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getOne(@LoginMember Long memberId) {
        return ResponseEntity.ok(memberService.getOne(memberId));
    }
}


