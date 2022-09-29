package com.reborn.reborn.article.domain;

import com.reborn.reborn.article.domain.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originFileName;
    private String uploadFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public ArticleImage(String originFileName, String uploadFileName){
        this.originFileName = originFileName;
        this.uploadFileName = uploadFileName;
    }

    public void uploadToArticle(Article article){
        if(this.article != null){
            this.article.getArticleImages().remove(this);
        }
        this.article = article;
        article.getArticleImages().add(this);
    }

}
