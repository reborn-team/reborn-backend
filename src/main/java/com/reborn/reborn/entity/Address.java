package com.reborn.reborn.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;

    //TODO 도로명 주소 변수명 변경해야함.
    private String roadName;

    private String zipcode;
}
