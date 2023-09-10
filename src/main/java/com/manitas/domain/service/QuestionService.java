package com.manitas.domain.service;

import com.manitas.application.dto.request.QuestionRequestDto;
import com.manitas.domain.data.entity.QuestionEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionService {
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    QuestionEntity createQuestion(QuestionRequestDto questionRequestDto) throws BusinessException;

    QuestionEntity updateQuestion(QuestionRequestDto questionRequestDto) throws BusinessException;

    QuestionEntity getQuestionById(String id) throws BusinessException;
}
