package com.reborn.reborn.gym.presentation.dto;

import com.reborn.reborn.gym.domain.Gym;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GymResponseDto {
    private Long id;
    private String place;
    private String addr;
    private Double lat;
    private Double lng;

    @Builder
    public GymResponseDto(Long id, String place, String addr, Double lat, Double lng){
        this.id = id;
        this.place = place;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
    }

    public static GymResponseDto of(Gym gym){
        return GymResponseDto.builder()
                .id(gym.getId())
                .place(gym.getPlace())
                .addr(gym.getAddr())
                .lat(gym.getLat())
                .lng(gym.getLng())
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class GymList {
        private List<GymResponseDto> list;
        public GymList(List<GymResponseDto> list) {
            this.list = list;
        }
    }
}
