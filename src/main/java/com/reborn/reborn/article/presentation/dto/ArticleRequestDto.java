package com.reborn.reborn.article.presentation.dto;

import com.reborn.reborn.common.presentation.dto.FileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {
    private String title;
    private String content;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();
}
