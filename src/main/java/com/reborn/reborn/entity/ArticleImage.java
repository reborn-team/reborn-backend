package com.reborn.reborn.entity;

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

    public ArticleImage(String originFileName, String uploadFileName,Article article){
        this.originFileName = originFileName;
        this.uploadFileName = uploadFileName;
        this.article =article;
    }

//    public void uploadToArticle(Article article){
//        if(this.article != null){
//            this.article.getArticleImages().remove(this);
//        }
//        this.article = article;
//        article.getArticleImages().add(this);
//    }

}
