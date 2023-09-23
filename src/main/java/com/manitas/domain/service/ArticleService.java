package com.manitas.domain.service;

import com.manitas.application.dto.request.ArticleRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.ArticleResponseDto;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;

public interface ArticleService {
    ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) throws BusinessException;

    ArticleResponseDto updateArticle(ArticleRequestDto articleRequestDto) throws BusinessException;

    ArticleEntity getArticleById(String id) throws BusinessException;

    ArticleResponseDto getArticleDtoById(String id) throws BusinessException;

    Page<ArticleResponseDto> getPage(RequestDto<ArticleRequestDto> articleDto);
}
