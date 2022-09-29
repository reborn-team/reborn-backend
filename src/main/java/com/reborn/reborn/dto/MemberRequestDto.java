package com.reborn.reborn.dto;

import com.reborn.reborn.entity.Address;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    @Email
    private String email;
    private String password;
    @NotNull
    @Length(min = 1, message = "수정할 이름의 길이는 1이상이어야 합니다.")
    private String nickname;
    private String phone;
    private String zipcode;
    private String roadName;
    private String detailAddress;


    public Member toEntity(MemberRequestDto memberRequestDto){
        return Member.builder()
                .nickname((memberRequestDto.getNickname()))
                .phone(memberRequestDto.getPhone())
                .email(memberRequestDto.getEmail())
                .address(new Address(memberRequestDto.getRoadName(), memberRequestDto.getDetailAddress(), memberRequestDto.getZipcode()))
                .memberRole(MemberRole.USER)
                .build();
    }
}
