package com.reborn.reborn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
