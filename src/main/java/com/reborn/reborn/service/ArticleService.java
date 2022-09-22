package com.reborn.reborn.service;

import com.reborn.reborn.dto.ArticleRequestDto;
import com.reborn.reborn.entity.Article;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.repository.ArticleRepository;
import com.reborn.reborn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(Long memberId, ArticleRequestDto articleRequest) {

        Member member = memberRepository.findById(memberId).orElseThrow();
        Article article = Article.builder()
                .member(member)
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent()).build();

        Article saveArticle = articleRepository.save(article);
        return saveArticle.getId();
    }

    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow();
    }

    @Transactional
    public ArticleRequestDto getArticleDto(Long articleId) { return articleRepository.getArticle)
    }
}
