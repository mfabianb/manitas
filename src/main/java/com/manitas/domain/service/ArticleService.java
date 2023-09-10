package com.manitas.domain.service;

import com.manitas.application.dto.request.ArticleRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;

public interface ArticleService {
    ArticleEntity createArticle(ArticleRequestDto articleRequestDto) throws BusinessException;

    ArticleEntity updateArticle(ArticleRequestDto articleRequestDto) throws BusinessException;

    ArticleEntity getArticleById(String id) throws BusinessException;

    Page<ArticleEntity> getPage(RequestDto<ArticleRequestDto> articleDto);
}
