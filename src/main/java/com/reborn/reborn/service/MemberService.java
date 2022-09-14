package com.reborn.reborn.service;

import com.reborn.reborn.dto.ChangePasswordDto;
import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.entity.Member;


public interface MemberService {

    Long registerMember(MemberRequestDto memberRequestDto);

    boolean emailDuplicateCheck(String name);

    void updatePassword(Member member, ChangePasswordDto changePasswordDto);
}
