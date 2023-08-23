package com.manitas.domain.service.impl;

import com.manitas.application.dto.ArticleDto;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.data.repository.ArticleRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.ArticleService;
import com.manitas.domain.service.CatalogService;
import com.manitas.domain.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.manitas.utils.Constants.*;

@Service
@Log4j2
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CatalogService catalogService;

    public ArticleEntity createArticle(ArticleDto articleDto) throws BusinessException {

        return articleRepository.save(
                ArticleEntity.builder()
                        .idArticle(UUID.randomUUID().toString())
                        .name(articleDto.getName())
                        .description(articleDto.getDescription())
                        .info(articleDto.getInfo())
                        .media(articleDto.getMedia())
                        .creationDate(LocalDateTime.now())
                        .idUser(userService.getUserByEmail(articleDto.getEmail()))
                        .idTopic(catalogService.getTopicById(articleDto.getIdTopic()))
                        .enable(Boolean.FALSE)
                        .build()
        );

    }

    private void validateMandatoryArticleDto(ArticleDto articleDto) throws BusinessException {
        if(Objects.isNull(articleDto) || Objects.isNull(articleDto.getName())
                || Objects.isNull(articleDto.getDescription()) || Objects.isNull(articleDto.getInfo())
                || Objects.isNull(articleDto.getIdTopic())) throw new BusinessException(SOME + ARTICLE + USER + SPACE + REQUIRED);
    }

}
