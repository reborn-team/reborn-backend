package com.reborn.reborn.article.application;

import com.reborn.reborn.article.presentation.dto.*;
import com.reborn.reborn.common.presentation.dto.FileDto;
import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.article.domain.ArticleImage;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.article.domain.repository.ArticleImageRepository;
import com.reborn.reborn.article.domain.repository.ArticleRepository;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final ArticleImageRepository articleImageRepository;

    public Article create(Long memberId, ArticleRequestDto articleRequest) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId.toString()));
        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .member(member)
                .build();

        Article saveArticle = articleRepository.save(article);

        List<FileDto> files = articleRequest.getFiles();
        if(files.size() > 0){
            files.forEach(file -> createImage(file, article));
        }
        return saveArticle;
    }

    public Page<ArticleListDto> pagingArticleBySearchCondition(ArticleSearchType articleSearchType, Pageable pageable){
        return articleRepository.searchArticlePaging(articleSearchType, pageable);
    }
    public Page<ArticleListDto> pagingArticleBySearchConditionWithMemberId(ArticleSearchType articleSearchType, Long memberId, Pageable pageable) {
        return articleRepository.searchArticlePagingByMemberId(articleSearchType, pageable, memberId);
    }

    public ArticleResponseDto getArticleDetailDto(Long memberId, Long articleId){
        Article article = articleRepository.findByIdWithImageAndMemberAndReplyCount(articleId).orElseThrow();
        article.addViewCount();
        ArticleResponseDto dto = ArticleResponseDto.of(article);
        dto.isAuthor(memberId);

        return dto;
    }

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
        if(!Objects.equals(article.getMember().getId(), authorId)){
            throw new RuntimeException("권한이 없음");
        }
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow();
    }

    public void deleteArticle(Long authorId, Long articleId) {
        Article article = getArticle(articleId);
        validIsAuthor(authorId, article);
        articleRepository.delete(article);
    }


}
