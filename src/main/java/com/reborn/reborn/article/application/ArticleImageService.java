package com.reborn.reborn.article.application;

import com.reborn.reborn.article.domain.Article;
import com.reborn.reborn.article.domain.ArticleImage;
import com.reborn.reborn.article.domain.repository.ArticleImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleImageService {

    private final ArticleImageRepository articleImageRepository;

    @Transactional
    public Long create(Article article, String originFileName, String uploadFileName){
        ArticleImage articleImage = new ArticleImage(originFileName, uploadFileName);
        articleImageRepository.save(articleImage);
        return articleImage.getId();
    }

}
