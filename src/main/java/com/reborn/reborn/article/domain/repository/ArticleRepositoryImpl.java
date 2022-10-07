package com.reborn.reborn.article.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.article.presentation.dto.ArticleListDto;
import com.reborn.reborn.article.presentation.dto.ArticleSearchType;
import com.reborn.reborn.article.presentation.dto.QArticleListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.reborn.reborn.article.domain.QArticle.article;
import static com.reborn.reborn.member.domain.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<ArticleListDto> searchArticlePaging(ArticleSearchType searchType, Pageable pageable) {

        List<ArticleListDto> result = queryFactory
                .select(new QArticleListDto(
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
                .orderBy(article.createdDate.desc())
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

    @Override
    public Page<ArticleListDto> searchArticlePagingByMemberId(ArticleSearchType searchType, Pageable pageable, Long memberId) {
        List<ArticleListDto> result = queryFactory
                .select(new QArticleListDto(
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
                        nicknameContains(searchType.getNickname()),
                        article.member.id.eq(memberId)
                )
                .orderBy(article.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(article.count())
                .from(article)
                .where(
                        nicknameContains(searchType.getNickname()),
                        titleContains(searchType.getTitle()),
                        article.member.id.eq(memberId)
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }


    private BooleanExpression nicknameContains(String nickname) {
        return hasText(nickname) ? article.member.nickname.contains(nickname) : null;
    }

    private BooleanExpression titleContains(String title) {
        return hasText(title) ? article.title.contains(title) : null;
    }

    @Override
    public Page<ArticleListDto> getPageByArticleViewCount(Pageable pageable) {
        List<ArticleListDto> result = queryFactory
                .select(new QArticleListDto(
                        article.id,
                        article.title,
                        article.member.nickname,
                        article.viewCount,
                        article.createdDate
                ))
                .from(article)
                .innerJoin(article.member, member)
                .orderBy(article.viewCount.desc(), article.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(article.count())
                .from(article);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne); 
    }

}
