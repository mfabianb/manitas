package com.manitas.domain.service;

import com.manitas.application.dto.request.QuestionnaireBlankDto;
import com.manitas.domain.exception.BusinessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionnaireBlankService {
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void createQuestionnaire(QuestionnaireBlankDto questionnaireBlankDto) throws BusinessException;
}
