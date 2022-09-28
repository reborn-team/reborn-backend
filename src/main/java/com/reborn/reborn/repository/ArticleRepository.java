package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Article;
import com.reborn.reborn.repository.custom.ArticleResponsitoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleResponsitoryQuerydsl {
    List<Article> findAllById(Long boardId);
}
