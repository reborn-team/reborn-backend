package com.reborn.reborn.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String originFileName;
    private String uploadFileName;
}
