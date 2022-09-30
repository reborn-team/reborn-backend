package com.reborn.reborn.comment.presentation;

import com.reborn.reborn.comment.application.CommentService;
import com.reborn.reborn.comment.domain.Comment;
import com.reborn.reborn.comment.presentation.dto.CommentRequestDto;
import com.reborn.reborn.comment.presentation.dto.CommentResponseDto;
import com.reborn.reborn.security.domain.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(@LoginMember Long memberId, @RequestBody CommentRequestDto commentRequestDto){
        Long saveCommentId = commentService.create(memberId, commentRequestDto);
        Comment comment = commentService.findCommentById(saveCommentId);
        return ResponseEntity.created(URI.create("/comment"+saveCommentId)).body(saveCommentId);
    }

//    @GetMapping("/{commentId}")
//    public ResponseEntity<CommentResponseDto> getComment()
}
