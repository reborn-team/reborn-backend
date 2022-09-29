package com.reborn.reborn.controller;

import com.reborn.reborn.dto.*;
import com.reborn.reborn.entity.Article;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.ArticleImageService;
import com.reborn.reborn.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequestMapping("/api/v1/articles")
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleImageService articleImageService;

    @PostMapping
    public ResponseEntity createArticle(@LoginMember Long memberId, @RequestBody ArticleRequestDto articleRequestDto){
        Long saveArticleId = articleService.create(memberId, articleRequestDto);
        Article article = articleService.findArticleById(saveArticleId);
        log.info("filename={}",articleRequestDto.getUploadFileName());
        articleImageService.create(article, articleRequestDto.getOriginFileName(), articleRequestDto.getUploadFileName());
        return ResponseEntity.created(URI.create("/api/v1/articles/" + saveArticleId)).body(saveArticleId);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<ArticleListDto>> getArticleList(@ModelAttribute ArticleSearchType articleSearchType, PageRequestDTO pageRequestDTO){
        Page<ArticleListDto> result = articleService.pagingArticleBySearchCondition(articleSearchType, pageRequestDTO.of());
        log.info("articleSearchType={}",articleSearchType);
        PageResponseDto<ArticleListDto> articleResponseDto = new PageResponseDto<>(result);
        return ResponseEntity.ok().body(articleResponseDto);
    }
}
