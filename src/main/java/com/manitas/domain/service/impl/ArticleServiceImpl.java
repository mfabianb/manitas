package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.ArticleRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.ArticleResponseDto;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.data.entity.MediaEntity;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.data.repository.ArticleRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.ArticleService;
import com.manitas.domain.service.CatalogService;
import com.manitas.domain.service.UserService;
import com.manitas.utils.ArticleUtility;
import com.manitas.utils.PageUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) throws BusinessException {

        validateMandatoryArticleDto(articleRequestDto);

        return ArticleUtility.entityToResponseDto(articleRepository.saveAndFlush(
                ArticleEntity.builder()
                        .idArticle(UUID.randomUUID().toString())
                        .name(articleRequestDto.getName())
                        .description(articleRequestDto.getDescription())
                        .info(articleRequestDto.getInfo())
                        .idMedia(MediaEntity.builder()
                                .idMedia(UUID.randomUUID().toString())
                                .media(articleRequestDto.getMedia().getMedia())
                                .type(articleRequestDto.getMedia().getType())
                                .source(articleRequestDto.getMedia().getSource())
                                .build())
                        .creationDate(LocalDateTime.now())
                        .idUser(userService.getUserByEmail(articleRequestDto.getEmail()))
                        .idTopic(catalogService.getTopicById(articleRequestDto.getIdTopic()))
                        .enable(Boolean.TRUE)
                        .build()
        ));

    }

    @Override
    public ArticleResponseDto updateArticle(ArticleRequestDto articleRequestDto) throws BusinessException {

        validateMandatoryArticleDto(articleRequestDto);

        ArticleEntity articleEntity = getArticleById(articleRequestDto.getIdArticle());

        validatePermissionToEdit(articleRequestDto, articleEntity);

        updateData(articleRequestDto, articleEntity);

        return ArticleUtility.entityToResponseDto(articleRepository.save(articleEntity));

    }

    @Override
    public ArticleEntity getArticleById(String id) throws BusinessException {
        Optional<ArticleEntity> optionalArticleEntity = articleRepository.findById(id);
        if(optionalArticleEntity.isPresent()) return optionalArticleEntity.get();
        else throw new BusinessException(ARTICLE + SPACE + NOT_FOUND);
    }

    @Override
    public ArticleResponseDto getArticleDtoById(String id) throws BusinessException {
        Optional<ArticleEntity> optionalArticleEntity = articleRepository.findById(id);
        if(optionalArticleEntity.isPresent()) return ArticleUtility.entityToResponseDto(optionalArticleEntity.get());
        else throw new BusinessException(ARTICLE + SPACE + NOT_FOUND);
    }

    @Override
    public Page<ArticleResponseDto> getPage(RequestDto<ArticleRequestDto> articleDto){

        return articleRepository.getArticleDtoPage(articleDto.getData().getName(), articleDto.getData().getDescription(),
                articleDto.getData().getInfo(), articleDto.getData().getCreationDate(), articleDto.getData().getModificationDate(),
                articleDto.getData().getEmail(), articleDto.getData().getIdTopic(), articleDto.getData().getEnable(),
                PageUtility.getPage(articleDto));

    }

    private void validateMandatoryArticleDto(ArticleRequestDto articleRequestDto) throws BusinessException {

        if(Objects.isNull(articleRequestDto) || Objects.isNull(articleRequestDto.getName())
                || Objects.isNull(articleRequestDto.getDescription()) || Objects.isNull(articleRequestDto.getInfo())
                || Objects.isNull(articleRequestDto.getIdTopic()) || Objects.isNull(articleRequestDto.getEmail()))
            throw new BusinessException(SOME + ARTICLE + SPACE + REQUIRED);

    }

    private void updateData(ArticleRequestDto dto, ArticleEntity entity){

        boolean result = !entity.getName().equals(dto.getName()) || !entity.getDescription().equals(dto.getDescription())
                || !entity.getInfo().equals(dto.getInfo())
                || !entity.getIdTopic().getIdTopic().equals(dto.getIdTopic()) || !entity.getEnable().equals(dto.getEnable());

        if(result) {
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setInfo(dto.getInfo());

            MediaEntity media = entity.getIdMedia();
            media.setMedia(dto.getMedia().getMedia());
            media.setSource(dto.getMedia().getSource());
            media.setType(dto.getMedia().getType());

            entity.setIdMedia(media);
            entity.setModificationDate(LocalDateTime.now());
            entity.setIdTopic(catalogService.getTopicById(dto.getIdTopic()));
            entity.setEnable(dto.getEnable());
        }

    }

    private void validatePermissionToEdit(ArticleRequestDto dto, ArticleEntity entity) throws BusinessException {
        UserEntity userEntity = userService.getUserByEmail(dto.getEmail());
        if(!entity.getIdUser().getIdUser().equals(userEntity.getIdUser()))
            throw new BusinessException(USER + SPACE + NOT_UPDATE + SPACE + ARTICLE);

    }

}
