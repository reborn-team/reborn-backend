package com.reborn.reborn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
