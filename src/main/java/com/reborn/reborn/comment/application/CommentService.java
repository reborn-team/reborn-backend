package com.reborn.reborn.comment.application;

import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.article.domain.repository.ArticleRepository;
import com.reborn.reborn.comment.domain.Comment;
import com.reborn.reborn.comment.domain.repository.CommentRepository;
import com.reborn.reborn.comment.presentation.dto.CommentRequestDto;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Comment findCommentById(Long commentId){ return commentRepository.findById(commentId).orElseThrow(); }

}
