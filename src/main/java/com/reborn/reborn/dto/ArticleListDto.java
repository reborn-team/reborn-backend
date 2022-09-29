package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleListDto {
    private Long id;
    private String title;
    private String memberNickname;
    private Integer viewCount;
    @JsonFormat(pattern = "yy/MM/dd")
    private LocalDateTime regDate;

    @QueryProjection
    public ArticleListDto(Long id, String title, String memberNickname, Integer viewCount, LocalDateTime regDate) {
        this.id = id;
        this.title = title;
        this.memberNickname = memberNickname;
        this.viewCount = viewCount;
        this.regDate = regDate;
    }
}
