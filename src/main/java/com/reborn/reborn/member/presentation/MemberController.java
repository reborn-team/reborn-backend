package com.reborn.reborn.member.presentation;

import com.reborn.reborn.member.presentation.dto.JoinResponse;
import com.reborn.reborn.member.presentation.dto.MemberRequest;
import com.reborn.reborn.member.presentation.dto.MemberResponse;
import com.reborn.reborn.member.presentation.dto.MemberEditForm;
import com.reborn.reborn.security.domain.LoginMember;
import com.reborn.reborn.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<JoinResponse> join(@RequestBody @Valid MemberRequest memberRequest) {
        Long memberId = memberService.registerMember(memberRequest);
        log.info("aa");
        return ResponseEntity.created(URI.create("/api/v1/members/" + memberId)).body(new JoinResponse(memberId));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> modify(@LoginMember Long memberId, @RequestBody @Valid MemberEditForm request) {
        memberService.updateMember(memberId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getOne(@LoginMember Long memberId) {
        return ResponseEntity.ok(memberService.getOne(memberId));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMember(@LoginMember Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}


