package com.reborn.reborn.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;
    private String name;
    private String phoneNum;
    private String postcode;
    private String address;
    private String detailAddress;

}
