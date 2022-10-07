package com.reborn.reborn.member.presentation.dto;

import com.reborn.reborn.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {

    private String email;
    private String nickname;
    private String phone;
    private String zipcode;
    private String roadName;
    private String detailAddress;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .zipcode(member.getAddress().getZipcode())
                .roadName(member.getAddress().getRoadName())
                .detailAddress(member.getAddress().getDetailAddress())
                .build();
    }
}
