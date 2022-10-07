package com.reborn.reborn.article.domain.repository;

import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.article.domain.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
    @Modifying
    @Query("delete from ArticleImage ai where ai.article = :article")
    void deleteAllByArticle(@Param("article") Article article);
}
