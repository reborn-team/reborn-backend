package com.reborn.reborn.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Embedded
    private Address address;

    @Builder
    public Member(String email, String password, String nickname, String phone, Address address, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.memberRole = memberRole;
    }

    public void modifyInfo(Member member){
        this.nickname = member.getNickname();
        this.phone = member.getPhone();
        this.address = member.getAddress();
    }
    public void changePassword(String password) {
        this.password = password;
    }
}
