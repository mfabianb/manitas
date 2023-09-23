package com.manitas.utils;

import com.manitas.application.dto.response.ArticleResponseDto;
import com.manitas.domain.data.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

public class ArticleUtility {

    private ArticleUtility(){/*Empty constructor to avoid sonnar lint*/}

    public static ArticleResponseDto entityToResponseDto(ArticleEntity entity){
        return ArticleResponseDto.builder()
                .idArticle(entity.getIdArticle())
                .creationDate(entity.getCreationDate())
                .description(entity.getDescription())
                .enable(entity.getEnable())
                .idMedia(entity.getIdMedia())
                .info(entity.getInfo())
                .name(entity.getName())
                .modificationDate(entity.getModificationDate())
                .authorEmail(entity.getIdUser().getEmail())
                .authorLastname(entity.getIdUser().getLastname())
                .authorName(entity.getIdUser().getName())
                .authorSecondLastname(entity.getIdUser().getSecondLastname())
                .idTopic(entity.getIdTopic().getIdTopic())
                .nameTopic(entity.getIdTopic().getName())
                .idAuthor(entity.getIdUser().getIdUser())
                .build();
    }

    public static List<ArticleResponseDto> listEntityToListResponseDto(List<ArticleEntity> articleEntityList){
        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();
        articleEntityList.forEach(a -> articleResponseDtoList.add(entityToResponseDto(a)));
        return articleResponseDtoList;
    }

}
