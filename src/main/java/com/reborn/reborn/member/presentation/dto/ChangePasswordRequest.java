package com.reborn.reborn.member.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotNull
    @Length(min = 1, max = 20)
    private String rawPassword;
    @NotNull
    @Length(min = 1,max = 20)
    private String changePassword;
}
