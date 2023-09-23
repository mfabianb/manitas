package com.manitas.domain.service;

import com.manitas.application.dto.request.*;
import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionnaireBlankService {
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void createQuestionnaireAndInterpellations(QuestionnaireManualBlankDto questionnaireManualBlankDto) throws BusinessException;

    void createQuestionnaireSteady(QuestionnaireSteadyRequestDto questionnaireDto) throws BusinessException;

    void createQuestionnaireDynamic(QuestionnaireDynamicBlankDto questionnaireDto) throws BusinessException;

    Page<InterpellationEntity> getInterpellationByQuestionnaire(String idQuestionnaire) throws BusinessException;

    QuestionnaireEntity createQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException;

    Page<QuestionnaireEntity> getQuestionnairePage(RequestDto<QuestionnaireBlankRequestDto> requestDto) throws BusinessException;
}
