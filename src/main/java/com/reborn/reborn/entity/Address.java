package com.reborn.reborn.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    //TODO 도로명 주소 변수명 변경해야함.
    private String roadName;

    private String detailAddress;

    private String zipcode;
}
