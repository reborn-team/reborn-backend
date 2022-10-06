package com.reborn.reborn.comment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reborn.reborn.comment.domain.Comment;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String memberNickname;
    private String content;
    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    private LocalDateTime regDate;

    private Long memberId;
    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;

    @Builder
    public CommentResponseDto(Long id, String memberNickname, String content, LocalDateTime regDate, Long memberId){
        this.id= id;
        this.memberNickname = memberNickname;
        this.content = content;
        this.regDate = regDate;
        this.memberId = memberId;
    }

    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .memberNickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .regDate(comment.getCreatedDate())
                .memberId(comment.getMember().getId())
                .build();
    }

    public void isAuthor(Long memberId){
        this.isAuthor = Objects.equals(this.memberId, memberId);
    }

}
