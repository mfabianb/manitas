package com.manitas.domain.service;

import com.manitas.application.dto.request.QuestionnaireBlankRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;

public interface QuestionnaireService {
    QuestionnaireEntity createQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException;

    QuestionnaireEntity updateQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException;

    QuestionnaireEntity getQuestionnaireById(String id) throws BusinessException;

    Page<QuestionnaireEntity> getQuestionnairePage(RequestDto<QuestionnaireBlankRequestDto> requestDto) throws BusinessException;
}
