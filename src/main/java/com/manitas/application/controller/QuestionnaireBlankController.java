package com.manitas.application.controller;

import com.manitas.application.dto.request.QuestionnaireDynamicBlankDto;
import com.manitas.application.dto.request.QuestionnaireManualBlankDto;
import com.manitas.application.dto.request.QuestionnaireSteadyRequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.QuestionnaireBlankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DataResponse<QuestionnaireEntity>> createQuestionnaireData(@RequestBody QuestionnaireManualBlankDto questionnaireManualBlankDto){
        log.info(questionnaireManualBlankDto);
        try{
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(),
                    questionnaireBlankService.createQuestionnaireData(questionnaireManualBlankDto.getQuestionnaire())) , HttpStatus.OK);
        }catch (Exception e){
            log.info(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/manual")
    public ResponseEntity<DataResponse<Object>> createQuestionnaireBlank(@RequestBody QuestionnaireManualBlankDto questionnaireManualBlankDto){
        log.info(questionnaireManualBlankDto);
        try{
            questionnaireBlankService.createQuestionnaireAndInterpellations(questionnaireManualBlankDto);
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), null), HttpStatus.OK);
        }catch (BusinessException e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.INTERNAL_SERVER_ERROR.value(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/steady")
    public ResponseEntity<DataResponse<Object>> createQuestionnaireSteadyBlank(@RequestBody QuestionnaireSteadyRequestDto questionnaireSteadyRequestDto){
        log.info(questionnaireSteadyRequestDto);
        try{
            questionnaireBlankService.createQuestionnaireSteady(questionnaireSteadyRequestDto);
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), null), HttpStatus.OK);
        }catch (BusinessException e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.INTERNAL_SERVER_ERROR.value(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/dynamic")
    public ResponseEntity<DataResponse<Object>> createQuestionnaireDynamicBlank(@RequestBody QuestionnaireDynamicBlankDto questionnaireDynamicBlankDto){
        log.info(questionnaireDynamicBlankDto);
        try{
            questionnaireBlankService.createQuestionnaireDynamic(questionnaireDynamicBlankDto);
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), null), HttpStatus.OK);
        }catch (BusinessException e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.INTERNAL_SERVER_ERROR.value(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
