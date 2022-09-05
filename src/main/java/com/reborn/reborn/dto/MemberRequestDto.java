package com.reborn.reborn.dto;

import com.reborn.reborn.entity.Address;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String zipcode;
    private String roadName;
    private String detailAddress;


    public Member toEntity(MemberRequestDto memberRequestDto){
        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .phone(memberRequestDto.getPhone())
                .email(memberRequestDto.getEmail())
                .address(new Address(memberRequestDto.getRoadName(), memberRequestDto.getDetailAddress(), memberRequestDto.getZipcode()))
                .memberRole(MemberRole.USER)
                .build();
        return member;
    }
}
