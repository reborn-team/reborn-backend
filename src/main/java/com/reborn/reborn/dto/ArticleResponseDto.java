package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reborn.reborn.entity.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String memberNickname;
    private String content;
    private List<FileDto> files = new ArrayList<>();
    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    private LocalDateTime regDate;
    private int viewCount;

    @Builder
    public ArticleResponseDto(Long id, String title, String memberNickname, String content, List<FileDto> files, LocalDateTime regDate, int viewCount){
        this.id = id;
        this.title = title;
        this.memberNickname = memberNickname;
        this.content = content;
        this.files = files;
        this.regDate = regDate;
        this.viewCount = viewCount;
    }

    public static ArticleResponseDto of(Article article){
        List<FileDto> fileDtos = new ArrayList<>();
        article.getArticleImages().forEach(image -> fileDtos.add(new FileDto(image.getOriginFileName(), image.getUploadFileName())));

        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .memberNickname(article.getMember().getNickname())
                .content(article.getContent())
                .files(fileDtos)
                .regDate(article.getCreatedDate())
                .viewCount(article.getViewCount())
                .build();

    }

}
