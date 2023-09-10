package com.manitas.domain.service;

import com.manitas.application.dto.request.AnswerRequestDto;
import com.manitas.domain.data.entity.AnswerEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerService {
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    AnswerEntity createAnswer(AnswerRequestDto answerRequestDto) throws BusinessException;

    AnswerEntity updateAnswer(AnswerRequestDto answerRequestDto) throws BusinessException;

    AnswerEntity getAnswerById(String id) throws BusinessException;
}
