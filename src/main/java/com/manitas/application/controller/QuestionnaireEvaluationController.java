package com.manitas.application.controller;

import com.manitas.application.dto.request.ReplyQuestionnaireFromUserRequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.QuestionnaireEvaluationService;
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
@RequestMapping("/evaluation")
public class QuestionnaireEvaluationController {

    @Autowired
    private QuestionnaireEvaluationService questionnaireEvaluationService;

    @PostMapping
    public ResponseEntity<DataResponse<Object>> replyQuestionnaire(@RequestBody ReplyQuestionnaireFromUserRequestDto replyDto){
        log.info("replyQuestionnaire: {}", replyDto);
        try{
            questionnaireEvaluationService.questionnaireReplyFromUser(replyDto);
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), null), HttpStatus.OK);
        }catch (BusinessException e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
