package com.manitas.domain.service;

import com.manitas.application.dto.ArticleDto;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.exception.BusinessException;

public interface ArticleService {
    ArticleEntity createArticle(ArticleDto articleDto) throws BusinessException;

    ArticleEntity updateArticle(ArticleDto articleDto) throws BusinessException;

    ArticleEntity getArticleById(String id) throws BusinessException;
}
