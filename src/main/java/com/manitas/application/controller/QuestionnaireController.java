package com.manitas.application.controller;

import com.manitas.application.dto.request.QuestionnaireBlankRequestDto;
import com.manitas.application.dto.request.QuestionnaireManualBlankDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.service.QuestionnaireService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping
    public ResponseEntity<DataResponse<QuestionnaireEntity>> createQuestionnaireData(@RequestBody QuestionnaireManualBlankDto questionnaireManualBlankDto){
        log.info(questionnaireManualBlankDto);
        try{
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(),
                    questionnaireService.createQuestionnaireData(questionnaireManualBlankDto.getQuestionnaire())) , HttpStatus.OK);
        }catch (Exception e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<QuestionnaireEntity>> updateQuestionnaireData(@PathVariable String id,
                                                                                     @RequestBody QuestionnaireManualBlankDto questionnaireManualBlankDto){
        questionnaireManualBlankDto.getQuestionnaire().setIdQuestionnaire(id);
        log.info(questionnaireManualBlankDto);
        try{
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(),
                    questionnaireService.updateQuestionnaireData(questionnaireManualBlankDto.getQuestionnaire())) , HttpStatus.OK);
        }catch (Exception e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<QuestionnaireEntity>>> getQuestionnairePage(@RequestBody RequestDto<QuestionnaireBlankRequestDto> requestDto){
        log.info(requestDto);
        try{
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(),
                    questionnaireService.getQuestionnairePage(requestDto)) , HttpStatus.OK);
        }catch (Exception e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<QuestionnaireEntity>> getQuestionnaireData(@PathVariable String id){
        log.info("getQuestionnaireData by id: {}", id);
        try{
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(),
                    questionnaireService.getQuestionnaireById(id)) , HttpStatus.OK);
        }catch (Exception e){
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(true, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
