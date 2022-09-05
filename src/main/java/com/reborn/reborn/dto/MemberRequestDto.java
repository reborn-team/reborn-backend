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
    private String phone;
    private String zipcode;
    private String roadName;
    private String detailAddress;

}
