package com.reborn.reborn.controller;

import com.reborn.reborn.dto.ArticleRequestDto;
import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.entity.Article;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.ArticleImageService;
import com.reborn.reborn.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
        articleImageService.create(article, articleRequestDto.getOriginFileName(), articleRequestDto.getUploadFileName());
        return ResponseEntity.created(URI.create("/api/v1/articles/" + saveArticleId)).body(saveArticleId);
    }

}
