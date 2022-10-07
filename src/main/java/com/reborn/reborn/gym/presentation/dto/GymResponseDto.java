package com.reborn.reborn.gym.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reborn.reborn.gym.domain.Gym;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class GymResponseDto {
    private Long id;
    private String place;
    private String addr;
    private Double lat;
    private Double lng;

    private Long memberId;
    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;

    @Builder
    public GymResponseDto(Long id, String place, String addr, Double lat, Double lng, Long memberId){
        this.id = id;
        this.place = place;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
        this.memberId = memberId;
    }

    public static GymResponseDto of(Gym gym){
        return GymResponseDto.builder()
                .id(gym.getId())
                .place(gym.getPlace())
                .addr(gym.getAddr())
                .lat(gym.getLat())
                .lng(gym.getLng())
                .memberId(gym.getMember().getId())
                .build();
    }

    public void isAuthor(Long memberId){
        this.isAuthor = Objects.equals(this.memberId, memberId);
    }

}
