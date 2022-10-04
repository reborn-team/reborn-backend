package com.reborn.reborn.comment.presentation.dto;

import com.reborn.reborn.comment.domain.Comment;
import lombok.*;

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

    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .memberNickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .regDate(comment.getCreatedDate())
                .build();
    }

}
