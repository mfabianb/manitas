package com.manitas.domain.service;

import com.manitas.application.dto.request.ReplyQuestionnaireFromUserRequestDto;
import com.manitas.domain.exception.BusinessException;

public interface QuestionnaireEvaluationService {
    void questionnaireReplyFromUser(ReplyQuestionnaireFromUserRequestDto replyDto) throws BusinessException;
}
