package com.reborn.reborn.article.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {
    private String title;
    private String content;
    private String originFileName;
    private String uploadFileName;
}
