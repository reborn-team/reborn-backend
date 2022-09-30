package com.reborn.reborn.comment.presentation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String memberNickname;
    private String content;
    private LocalDateTime regDate;

    @Builder
    public CommentResponseDto(Long id, String memberNickname, String content, LocalDateTime regDate){
        this.id= id;
        this.memberNickname = memberNickname;
        this.content = content;
        this.regDate = regDate;
    }
}
