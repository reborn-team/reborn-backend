package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<FileDto> files = new ArrayList<>();
    private Long memberId;

    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;

    private String memberNickname;
    private String originFileName;
    private String uploadFileName;
    private String likeCount;
    private String viewCount;
}
