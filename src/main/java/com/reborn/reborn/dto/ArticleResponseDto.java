package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String memberNickname;
    private Integer viewCount;
    @JsonFormat(pattern = "yy/MM/dd")
    private LocalDateTime regDate;

    @QueryProjection
    public ArticleResponseDto(Long id, String title, String memberNickname, Integer viewCount, LocalDateTime regDate) {
        this.id = id;
        this.title = title;
        this.memberNickname = memberNickname;
        this.viewCount = viewCount;
        this.regDate = regDate;
    }
}
