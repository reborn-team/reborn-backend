package com.reborn.reborn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEditForm {
    private String title;
    private String content;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();
}
