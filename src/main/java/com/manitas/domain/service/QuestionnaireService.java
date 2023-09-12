package com.manitas.domain.service;

import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.exception.BusinessException;

public interface QuestionnaireService {
    QuestionnaireEntity createQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException;

    QuestionnaireEntity updateQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException;
}
