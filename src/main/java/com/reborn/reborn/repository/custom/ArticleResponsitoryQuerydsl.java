package com.reborn.reborn.repository.custom;

import com.reborn.reborn.dto.ArticleListDto;
import com.reborn.reborn.dto.ArticleSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleResponsitoryQuerydsl {
    Page<ArticleListDto> searchArticlePaging(ArticleSearchType searchType, Pageable pageable);
}
