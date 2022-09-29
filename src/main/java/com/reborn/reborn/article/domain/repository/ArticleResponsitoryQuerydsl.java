package com.reborn.reborn.article.domain.repository;

import com.reborn.reborn.article.presentation.dto.ArticleListDto;
import com.reborn.reborn.article.presentation.dto.ArticleSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleResponsitoryQuerydsl {
    Page<ArticleListDto> searchArticlePaging(ArticleSearchType searchType, Pageable pageable);
}
