package com.reborn.reborn.comment.application;

import com.reborn.reborn.comment.domain.Comment;
import com.reborn.reborn.comment.domain.repository.CommentRepository;
import com.reborn.reborn.comment.presentation.dto.CommentRequestDto;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public Long create(Long memberId, CommentRequestDto commentRequestDto) {

        Member member = memberRepository.findById(memberId).orElseThrow();
        Comment comment = Comment.builder()
                .member(member)
                .content(commentRequestDto.getContent())
                .build();

        Comment saveComment = commentRepository.save(comment);
        return saveComment.getId();
    }

    public Comment findCommentById(Long commentId){ return commentRepository.findById(commentId).orElseThrow(); }

}
