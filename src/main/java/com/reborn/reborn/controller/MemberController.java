package com.reborn.reborn.controller;

import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody MemberRequestDto memberRequestDto){
        memberService.registerMember(memberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
