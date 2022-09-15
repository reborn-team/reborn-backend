package com.reborn.reborn.controller;

import com.reborn.reborn.dto.ChangePasswordDto;
import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody MemberRequestDto memberRequestDto) {
        Long memberId = memberService.registerMember(memberRequestDto);
        log.info("aa");
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }

    @GetMapping("/email-check")
    public ResponseEntity emailCheck(@RequestParam String email) {
        boolean check = memberService.emailDuplicateCheck(email);

        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    @PatchMapping("/change-password")
    public ResponseEntity changePassword(@LoginMember Member member, @RequestBody ChangePasswordDto changePasswordDto){
        memberService.updatePassword(member, changePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
