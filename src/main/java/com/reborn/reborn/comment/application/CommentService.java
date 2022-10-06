package com.reborn.reborn.comment.application;

import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.article.domain.repository.ArticleRepository;
import com.reborn.reborn.comment.domain.Comment;
import com.reborn.reborn.comment.domain.repository.CommentRepository;
import com.reborn.reborn.comment.presentation.dto.CommentEditForm;
import com.reborn.reborn.comment.presentation.dto.CommentRequestDto;
import com.reborn.reborn.comment.presentation.dto.CommentResponseDto;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Long create(Long memberId, Long articleId, CommentRequestDto dto) {

        Member member = memberRepository.findById(memberId).orElseThrow();
        Article article = articleRepository.findById(articleId).orElseThrow();
        Comment comment = Comment.builder()
                .member(member)
                .article(article)
                .content(dto.getContent())
                .build();

        Comment saveComment = commentRepository.save(comment);
        return saveComment.getId();
    }

    public List<CommentResponseDto> getCommentList(Long articleId) {

        return commentRepository.findAllByArticleIdWithMember(articleId).stream()
                .map(comment -> CommentResponseDto.of(comment))
                .collect(Collectors.toList());
    }

    public Comment updateComment(Long authorId, Long commentId, CommentEditForm form) {
        Comment comment = getComment(commentId);
        validIsAuthor(authorId, comment);
        comment.modifyComment(form.getContent());

        return comment;
    }

    private void validIsAuthor(Long authorId, Comment comment) {
        if(!Objects.equals(comment.getMember().getId(), authorId)){
            throw new RuntimeException("권한이 없음");
        }
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }

    public void deleteComment(Long authorId, Long commentId) {
        Comment comment = getComment(commentId);
        validIsAuthor(authorId, comment);
        commentRepository.delete(comment);
    }

}
