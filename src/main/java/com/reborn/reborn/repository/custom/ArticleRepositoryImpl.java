package com.reborn.reborn.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.dto.ArticleResponseDto;
import com.reborn.reborn.dto.ArticleSearchType;
import com.reborn.reborn.dto.QArticleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.reborn.reborn.entity.QArticle.*;
import static com.reborn.reborn.entity.QMember.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleResponsitoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<ArticleResponseDto> searchArticlePaging(ArticleSearchType searchType, Pageable pageable) {

        List<ArticleResponseDto> result = queryFactory
                .select(new QArticleResponseDto(
                        article.id,
                        article.title,
                        article.member.nickname,
                        article.viewCount,
                        article.createdDate
                ))
                .from(article)
                .innerJoin(article.member, member)
                .where(
                        titleContains(searchType.getTitle()),
                        nicknameContains(searchType.getNickname())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(article.count())
                .from(article)
                .where(
                        nicknameContains(searchType.getNickname()),
                        titleContains(searchType.getTitle())
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }





    private BooleanExpression nicknameContains(String nickname) {
        return hasText(nickname) ? article.member.nickname.contains(nickname) : null;
    }

    private BooleanExpression titleContains(String title) {
        return hasText(title) ? article.title.contains(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return hasText(content) ? article.content.contains(content) : null;
    }

}
