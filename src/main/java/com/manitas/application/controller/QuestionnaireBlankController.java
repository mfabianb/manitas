package com.manitas.application.controller;

import com.manitas.application.dto.request.*;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.QuestionnaireBlankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/blank")
public class QuestionnaireBlankController {

    @Autowired
    private QuestionnaireBlankService questionnaireBlankService;

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

    @GetMapping("/{blankKey}")
    public ResponseEntity<DataResponse<List<InterpellationResponseDto>>> getQuestionnaireBlankByKey(@PathVariable(value="blankKey") String blankKey){
        log.info("getQuestionnaireBlankByKey: {}", blankKey);
        return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), questionnaireBlankService.getAllInterpellationToReplyDto(blankKey)), HttpStatus.OK);
    }

}
