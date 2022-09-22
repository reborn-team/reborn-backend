package com.reborn.reborn.dto;

import com.reborn.reborn.entity.Address;
import com.reborn.reborn.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {
    private String nickname;
    private String phone;
    private String zipcode;
    private String roadName;
    private String detailAddress;

    public Member toEntity(MemberUpdateRequest request) {
        return Member.builder()
                .nickname(request.getNickname())
                .phone(request.getPhone())
                .address(new Address(request.getRoadName(), request.getDetailAddress(), request.getZipcode()))
                .build();
    }
}
