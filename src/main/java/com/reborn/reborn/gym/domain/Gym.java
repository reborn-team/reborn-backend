package com.reborn.reborn.gym.domain;

import com.reborn.reborn.common.domain.BaseTimeEntity;
import com.reborn.reborn.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gym extends BaseTimeEntity {

    @Id
    @Column(name = "gym_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String place;
    private String addr;
    private Double lat;
    private Double lng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Gym(String place, String addr, Double lat, Double lng, Member member){
        this.place = place;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
        this.member = member;
    }

    

}
