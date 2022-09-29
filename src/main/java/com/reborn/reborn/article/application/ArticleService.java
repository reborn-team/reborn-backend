package com.reborn.reborn.article.application;

import com.reborn.reborn.article.presentation.dto.*;
import com.reborn.reborn.common.presentation.dto.FileDto;
import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.article.domain.ArticleImage;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.article.domain.repository.ArticleImageRepository;
import com.reborn.reborn.article.domain.repository.ArticleRepository;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ArticleImageRepository articleImageRepository;

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

    public Page<ArticleListDto> pagingArticleBySearchCondition(ArticleSearchType articleSearchType, Pageable pageable){
        return articleRepository.searchArticlePaging(articleSearchType, pageable);
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticleDetailDto(Long memberId, Long articleId){
        Article article = articleRepository.findByIdWithImageAndMemberAndReplyCount(articleId).orElseThrow();
        ArticleResponseDto dto = ArticleResponseDto.of(article);
        return dto;
    }

    @Transactional
    public Article updateWorkout(Long authorId, Long articleId, ArticleEditForm form) {
        Article article = getArticle(articleId);
        validIsAuthor(authorId, article);
        article.modifyArticle(form.getTitle(), form.getContent());

        deleteAndUpdateImage(form.getFiles(), article);
        return article;
    }


    private void deleteAndUpdateImage(List<FileDto> files, Article article) {
        if(files.size() == 0){
            return;
        }
        articleImageRepository.deleteAllByArticle(article);
        files.forEach(file -> createImage(file, article));
    }

    public Long createImage(FileDto fileDto, Article article){
        ArticleImage articleImage = new ArticleImage(fileDto.getOriginFileName(), fileDto.getUploadFileName());
        articleImage.uploadToArticle(article);
        articleImageRepository.save(articleImage);
        return articleImage.getId();
    }

    private void validIsAuthor(Long authorId, Article article) {
        if(article.getMember().getId() != authorId){
            throw new RuntimeException("권한이 없음");
        }
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow();
    }

}
