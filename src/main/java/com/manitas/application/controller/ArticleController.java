package com.manitas.application.controller;

import com.manitas.application.dto.request.ArticleRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public DataResponse<ArticleEntity> createArticle(@RequestBody ArticleRequestDto articleRequestDto){
        log.info(articleRequestDto);
        try{
            return new DataResponse<>(true, null, HttpStatus.OK.value(), articleService.createArticle(articleRequestDto));
        }catch (BusinessException e){
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

    @PutMapping("/{idArticle}")
    public DataResponse<ArticleEntity> updateArticle(@PathVariable(value = "idArticle") String idArticle, @RequestBody ArticleRequestDto articleRequestDto){
        articleRequestDto.setIdArticle(idArticle);
        log.info(articleRequestDto);
        try{
            return new DataResponse<>(true, null, HttpStatus.OK.value(), articleService.updateArticle(articleRequestDto));
        }catch (BusinessException e){
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

    @GetMapping("/{idArticle}")
    public DataResponse<ArticleEntity> getArticle(@PathVariable(value = "idArticle") String idArticle){
        log.info("getArticle by id {}", idArticle);
        try{
            return new DataResponse<>(true, null, HttpStatus.OK.value(), articleService.getArticleById(idArticle));
        }catch (BusinessException e){
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

    @GetMapping
    public DataResponse<Page<ArticleEntity>> getArticleList(@RequestBody RequestDto<ArticleRequestDto> articleDtoDataResponse){
        log.info(articleDtoDataResponse);
        try{
            return new DataResponse<>(true, null, HttpStatus.OK.value(), articleService.getPage(articleDtoDataResponse));
        }catch (Exception e){
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

}
