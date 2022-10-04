package com.reborn.reborn.article.presentation;

import com.reborn.reborn.article.presentation.dto.*;
import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.security.domain.LoginMember;
import com.reborn.reborn.article.application.ArticleImageService;
import com.reborn.reborn.article.application.ArticleService;
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

    @PostMapping
    public ResponseEntity<Long> createArticle(@LoginMember Long memberId, @RequestBody ArticleRequestDto articleRequestDto){
        Article article = articleService.create(memberId, articleRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/articles/" + article.getId())).body(article.getId());
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<ArticleListDto>> getArticleList(@ModelAttribute ArticleSearchType articleSearchType, PageRequestDTO pageRequestDTO){
        Page<ArticleListDto> result = articleService.pagingArticleBySearchCondition(articleSearchType, pageRequestDTO.of());
        PageResponseDto<ArticleListDto> articleResponseDto = new PageResponseDto<>(result);
        return ResponseEntity.ok().body(articleResponseDto);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticleDetail(@PathVariable Long articleId ){
        ArticleResponseDto dto = articleService.getArticleDetailDto(articleId);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{articleId}")
    public ResponseEntity<Void> editArticle(@LoginMember Long memberId, @PathVariable Long articleId, @RequestBody ArticleEditForm form){
        articleService.updateWorkout(memberId, articleId, form);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@LoginMember Long memberId, @PathVariable Long articleId){
        articleService.deleteArticle(memberId, articleId);
        return ResponseEntity.noContent().build();
    }


}
