package com.manitas.application.controller;

import com.manitas.application.dto.request.ArticleRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.ArticleResponseDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.data.entity.ArticleEntity;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<DataResponse<ArticleResponseDto>> createArticle(@RequestBody ArticleRequestDto articleRequestDto){
        log.info(articleRequestDto);
        HttpStatus httpStatus;
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, httpStatus.value(), articleService.createArticle(articleRequestDto)), httpStatus);
        }catch (BusinessException e){
            log.info(e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), httpStatus.value(), null), httpStatus);
        }
    }

    @PutMapping("/{idArticle}")
    public ResponseEntity<DataResponse<ArticleResponseDto>> updateArticle(@PathVariable(value = "idArticle") String idArticle, @RequestBody ArticleRequestDto articleRequestDto){
        HttpStatus httpStatus;
        articleRequestDto.setIdArticle(idArticle);
        log.info(articleRequestDto);
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, httpStatus.value(), articleService.updateArticle(articleRequestDto)), httpStatus);
        }catch (BusinessException e){
            log.info(e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), httpStatus.value(), null), httpStatus);
        }
    }

    @GetMapping("/{idArticle}")
    public ResponseEntity<DataResponse<ArticleResponseDto>> getArticle(@PathVariable(value = "idArticle") String idArticle){
        HttpStatus httpStatus;
        log.info("getArticle by id {}", idArticle);
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), articleService.getArticleDtoById(idArticle)), httpStatus);
        }catch (BusinessException e){
            log.info(e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), httpStatus.value(), null), httpStatus);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<ArticleResponseDto>>> getArticleList(@RequestBody RequestDto<ArticleRequestDto> articleDtoDataResponse){
        log.info(articleDtoDataResponse);
        HttpStatus httpStatus;
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), articleService.getPage(articleDtoDataResponse)), httpStatus);
        }catch (Exception e){
            log.info(e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), httpStatus.value(), null), httpStatus);
        }
    }

}
