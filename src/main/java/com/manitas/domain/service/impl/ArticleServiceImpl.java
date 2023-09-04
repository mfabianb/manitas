package com.manitas.domain.service.impl;

import com.manitas.application.dto.ArticleDto;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.data.repository.ArticleRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.ArticleService;
import com.manitas.domain.service.CatalogService;
import com.manitas.domain.service.UserService;
import jdk.vm.ci.meta.Local;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
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

    @Override
    public ArticleEntity createArticle(ArticleDto articleDto) throws BusinessException {

        validateMandatoryArticleDto(articleDto);

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

    @Override
    public ArticleEntity updateArticle(ArticleDto articleDto) throws BusinessException {

        validateMandatoryArticleDto(articleDto);

        ArticleEntity articleEntity = getArticleById(articleDto.getId());

        validatePermissionToEdit(articleDto, articleEntity);

        updateData(articleDto, articleEntity);

        return articleRepository.save(articleEntity);

    }

    @Override
    public ArticleEntity getArticleById(String id) throws BusinessException {
        Optional<ArticleEntity> optionalArticleEntity = articleRepository.findById(id);
        if(optionalArticleEntity.isPresent()) return optionalArticleEntity.get();
        else throw new BusinessException(ARTICLE + SPACE + NOT_FOUND);
    }

    public Page<ArticleEntity> getPage(){
        return null;
    }

    private void validateMandatoryArticleDto(ArticleDto articleDto) throws BusinessException {

        if(Objects.isNull(articleDto) || Objects.isNull(articleDto.getName())
                || Objects.isNull(articleDto.getDescription()) || Objects.isNull(articleDto.getInfo())
                || Objects.isNull(articleDto.getIdTopic()) || Objects.isNull(articleDto.getEmail()))
            throw new BusinessException(SOME + ARTICLE + SPACE + REQUIRED);

    }

    private void updateData(ArticleDto dto, ArticleEntity entity){

        boolean result = !entity.getName().equals(dto.getName()) || !entity.getDescription().equals(dto.getDescription())
                || !entity.getInfo().equals(dto.getInfo()) || !entity.getMedia().equals(dto.getMedia())
                || !entity.getIdTopic().getIdTopic().equals(dto.getIdTopic()) || !entity.getEnable().equals(dto.getEnable());

        if(result) {
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setInfo(dto.getInfo());
            entity.setMedia(dto.getMedia());
            entity.setModificationDate(LocalDateTime.now());
            entity.setIdTopic(catalogService.getTopicById(dto.getIdTopic()));
            entity.setEnable(dto.getEnable());
        }

    }

    private void validatePermissionToEdit(ArticleDto dto, ArticleEntity entity) throws BusinessException {
        UserEntity userEntity = userService.getUserByEmail(dto.getEmail());
        if(!entity.getIdUser().getIdUser().equals(userEntity.getIdUser()))
            throw new BusinessException(USER + SPACE + NOT_UPDATE + SPACE + ARTICLE);

    }

}
