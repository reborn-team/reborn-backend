package com.reborn.reborn.article.domain.repository;

import com.reborn.reborn.article.presentation.dto.ArticleListDto;
import com.reborn.reborn.article.presentation.dto.ArticleSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryQuerydsl {
    Page<ArticleListDto> searchArticlePaging(ArticleSearchType searchType, Pageable pageable);

    Page<ArticleListDto> searchArticlePagingByMemberId(ArticleSearchType articleSearchType, Pageable pageable, Long memberId);

    Page<ArticleListDto> getPageByArticleViewCount(Pageable pageable);

}