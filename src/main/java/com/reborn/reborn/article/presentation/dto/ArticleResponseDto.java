package com.reborn.reborn.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.common.presentation.dto.FileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private Long memberId;
    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;

    @Builder
    public ArticleResponseDto(Long id, String title, String memberNickname, String content, List<FileDto> files, LocalDateTime regDate, int viewCount, Long memberId){
        this.id = id;
        this.title = title;
        this.memberNickname = memberNickname;
        this.content = content;
        this.files = files;
        this.regDate = regDate;
        this.viewCount = viewCount;
        this.memberId = memberId;
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
                .memberId(article.getMember().getId())
                .regDate(article.getCreatedDate())
                .viewCount(article.getViewCount())
                .build();
    }

    public void isAuthor(Long memberId){
        this.isAuthor = Objects.equals(this.memberId, memberId);
    }
}
