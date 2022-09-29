package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Article;
import com.reborn.reborn.repository.custom.ArticleResponsitoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleResponsitoryQuerydsl {
    @Query(value = "select a from Article a left join fetch a.articleImages i join fetch a.member where a.id = :id")
    Optional<Article> findByIdWithImageAndMemberAndReplyCount(@Param("id") Long boardId);
}
