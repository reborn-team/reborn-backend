package com.reborn.reborn.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    private String roadName;

    private String detailAddress;

    private String zipcode;
}
