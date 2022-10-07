package com.reborn.reborn.comment.application;

import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.article.domain.repository.ArticleRepository;
import com.reborn.reborn.comment.domain.Comment;
import com.reborn.reborn.comment.domain.repository.CommentRepository;
import com.reborn.reborn.comment.presentation.dto.CommentRequestDto;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("댓글 생성")
    void createComment() {
        Article article = Article.builder().build();
        Member member = Member.builder().build();
        CommentRequestDto commentRequestDto = new CommentRequestDto("댓글");
        Comment comment = Comment.builder().id(1L).article(article).member(member).build();

        given(commentRepository.save(any())).willReturn(comment);
        given(articleRepository.findById(any())).willReturn(Optional.of(article));
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        Long saveId = commentService.create(member.getId(), article.getId(), commentRequestDto);

        verify(commentRepository).save(any());
        verify(articleRepository).findById(any());
        verify(memberRepository).findById(any());

        assertThat(saveId).isEqualTo(comment.getId());
    }


}