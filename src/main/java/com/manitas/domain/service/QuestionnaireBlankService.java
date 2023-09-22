package com.manitas.domain.service;

import com.manitas.application.dto.request.QuestionnaireManualBlankDto;
import com.manitas.application.dto.request.QuestionnaireSteadyRequestDto;
import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionnaireBlankService {
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void createQuestionnaireAndInterpellations(QuestionnaireManualBlankDto questionnaireManualBlankDto) throws BusinessException;

    void createQuestionnaireByIdInterpellations(QuestionnaireSteadyRequestDto questionnaireDto) throws BusinessException;

    Page<InterpellationEntity> getInterpellationByQuestionnaire(String idQuestionnaire) throws BusinessException;

    QuestionnaireEntity createQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException;
}
