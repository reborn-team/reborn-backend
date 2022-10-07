package com.reborn.reborn.member.presentation;

import com.reborn.reborn.member.presentation.dto.ChangePasswordRequest;
import com.reborn.reborn.member.presentation.dto.DuplicateCheckResponse;
import com.reborn.reborn.security.domain.LoginMember;
import com.reborn.reborn.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;

    @GetMapping("/email-check")
    public ResponseEntity<DuplicateCheckResponse> emailCheck(@RequestParam String email) {
        boolean check = memberService.emailDuplicateCheck(email);
        return ResponseEntity.status(HttpStatus.OK).body(new DuplicateCheckResponse(check));
    }

    @GetMapping("/nickname-check")
    public ResponseEntity<DuplicateCheckResponse> nicknameCheck(@RequestParam String nickname) {
        boolean check = memberService.nicknameDuplicateCheck(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(new DuplicateCheckResponse(check));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@LoginMember Long memberId, @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        memberService.changePassword(memberId, changePasswordRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
