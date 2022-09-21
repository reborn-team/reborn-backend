package com.reborn.reborn.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String roadName;

    private String detailAddress;

    private String zipcode;
}
