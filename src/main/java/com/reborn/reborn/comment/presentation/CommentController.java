package com.reborn.reborn.comment.presentation;

import com.reborn.reborn.comment.application.CommentService;
import com.reborn.reborn.comment.presentation.dto.CommentEditForm;
import com.reborn.reborn.comment.presentation.dto.CommentRequestDto;
import com.reborn.reborn.comment.presentation.dto.CommentResponseDto;
import com.reborn.reborn.security.domain.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api/v1/articles")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}/comments")
    public ResponseEntity createComment(@LoginMember Long memberId, @PathVariable("articleId") Long articleId, @RequestBody CommentRequestDto commentRequestDto) {
        Long commentId = commentService.create(memberId, articleId, commentRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/articles/" + articleId)).body(commentId);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId){
        CommentResponseDto dto = commentService.getCommentDetail(commentId);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> editComment(@LoginMember Long memberId, @PathVariable Long commentId, @RequestBody CommentEditForm from){
        commentService.updateComment(memberId, commentId, from);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@LoginMember Long memberId, @PathVariable Long commentId){
        commentService.deleteComment(memberId, commentId);
        return ResponseEntity.noContent().build();
    }

}
