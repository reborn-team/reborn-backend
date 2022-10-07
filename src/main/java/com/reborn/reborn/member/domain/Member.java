package com.reborn.reborn.member.domain;

import javax.persistence.*;

import com.reborn.reborn.common.domain.BaseTimeEntity;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements Serializable {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Embedded
    private Address address;

    @Builder
    public Member(Long id, String email, String password, String nickname, String phone, Address address, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
    }

    public void modifyInfo(Member member) {
        this.nickname = member.getNickname();
        this.phone = member.getPhone();
        this.address = member.getAddress();
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
