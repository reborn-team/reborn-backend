package com.reborn.reborn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private String originFileName;
    private String uploadFileName;
}
