package com.manitas.application.controller;

import com.manitas.application.dto.request.QuestionnaireBlankDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.QuestionnaireBlankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/questionnaire")
public class QuestionnaireBlankController {

    @Autowired
    private QuestionnaireBlankService questionnaireBlankService;

    @PostMapping
    public DataResponse<Object> createQuestionnaireBlank(@RequestBody QuestionnaireBlankDto questionnaireBlankDto){
        try{
            questionnaireBlankService.createQuestionnaire(questionnaireBlankDto);
            return new DataResponse<>(true, null, HttpStatus.OK.value(), null);
        }catch (BusinessException e){
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

}
