package com.reborn.reborn.gym.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymRequestDto {
    private String place;
    private String addr;
    private Double lat;
    private Double lng;
}
