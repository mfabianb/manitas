package com.manitas.domain.service;

import com.manitas.application.dto.request.*;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.data.entity.QuestionnaireBlankEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionnaireBlankService {
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void createQuestionnaireAndInterpellations(QuestionnaireManualBlankDto questionnaireManualBlankDto) throws BusinessException;

    void createQuestionnaireSteady(QuestionnaireSteadyRequestDto questionnaireDto) throws BusinessException;

    void createQuestionnaireDynamic(QuestionnaireDynamicBlankDto questionnaireDto) throws BusinessException;

    List<InterpellationResponseDto> getAllInterpellationToReplyDto(String blankKey);

    QuestionnaireBlankEntity getBlankById(String id) throws BusinessException;

    long countInterpellationForQuestionnaireByBlankKey(String blankKey);
}
