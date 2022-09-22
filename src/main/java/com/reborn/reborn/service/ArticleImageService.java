package com.reborn.reborn.service;

import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.entity.Article;
import com.reborn.reborn.entity.ArticleImage;
import com.reborn.reborn.repository.ArticleImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleImageService {

    private final ArticleImageRepository articleImageRepository;

    @Transactional
    public Long create(Article article, String originFileName, String uploadFileName){
        ArticleImage articleImage = new ArticleImage(originFileName, uploadFileName, article);
        articleImageRepository.save(articleImage);
        return articleImage.getId();
    }

}
