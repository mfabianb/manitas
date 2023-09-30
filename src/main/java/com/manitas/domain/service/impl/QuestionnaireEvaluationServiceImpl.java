package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.ReplyQuestionnaireFromUserRequestDto;
import com.manitas.domain.data.entity.QuestionnaireAnsweredEntity;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.data.entity.UserResultEntity;
import com.manitas.domain.data.repository.QuestionnaireAnsweredRepository;
import com.manitas.domain.data.repository.UserResultRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Log4j2
public class QuestionnaireEvaluationServiceImpl implements QuestionnaireEvaluationService {

    @Autowired
    private QuestionnaireBlankService questionnaireBlankService;

    @Autowired
    private QuestionnaireAnsweredRepository questionnaireAnsweredRepository;

    @Autowired
    private UserResultRepository userResultRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void questionnaireReplyFromUser(ReplyQuestionnaireFromUserRequestDto replyDto) throws BusinessException {
        UserEntity userEntity = userService.getUserEntity(replyDto.getIdUser());

        List<QuestionnaireAnsweredEntity> questionnaireAnsweredEntityList = new ArrayList<>();

        long count = questionnaireBlankService.countInterpellationForQuestionnaireByBlankKey(replyDto.getBlankKey());

        if(replyDto.getAnswers().size() != count) throw new BusinessException("EL NÚMERO DE RESPUESTAS Y PREGUNTAS DEBE DE SER IGUAL");

        replyDto.getAnswers().forEach(a->{
            try {
                questionnaireAnsweredEntityList.add(
                        QuestionnaireAnsweredEntity.builder()
                                .idQuestionnaireAnswered(UUID.randomUUID().toString())
                                .idQuestionnaireBlank(questionnaireBlankService.getBlankById(a.getIdQuestionnaireBlank()))
                                .idUser(userEntity)
                                .blankKey(replyDto.getBlankKey())
                                .userAnswer(a.getAnswer())
                                .build()
                );
            } catch (BusinessException e) {
                log.info(e);
            }
        });

        if(questionnaireAnsweredEntityList.size() != count) throw new BusinessException("EL NÚMERO DE RESPUESTAS GUARDADAS Y PREGUNTAS DEBE DE SER IGUAL");

        questionnaireAnsweredRepository.saveAll(questionnaireAnsweredEntityList);

        log.info("RESPUESTAS DEL CUESTIONARIO GUARDADAS");

        evaluateQuestionnaire(userEntity, replyDto.getBlankKey());

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void evaluateQuestionnaire(UserEntity userEntity, String blankKey) throws BusinessException {

        List<QuestionnaireAnsweredEntity> questionnaireAnsweredEntityList = questionnaireAnsweredRepository.findByIdUserAndBlankKey(blankKey, userEntity);

        if(questionnaireAnsweredEntityList.isEmpty()) throw new BusinessException("EL CUESTIONARIO DEBE DE ESTAR RESPONDIDO");

        QuestionnaireEntity questionnaireEntity = questionnaireAnsweredEntityList.get(0).getIdQuestionnaireBlank().getIdQuestionnaire();

        AtomicLong countCorrect = new AtomicLong();

        questionnaireAnsweredEntityList.forEach(q -> {
            if(Boolean.TRUE.equals(q.getIdQuestionnaireBlank().getIdInterpellation().getCorrect())
                    && q.getIdQuestionnaireBlank().getIdInterpellation().getIdAnswer().getAnswer().equals(q.getUserAnswer()))
                countCorrect.getAndIncrement();
        });

        userResultRepository.save(UserResultEntity.builder()
                .idUserResult(UUID.randomUUID().toString())
                .idUser(userEntity)
                .idQuestionnaire(questionnaireEntity)
                .creationDate(LocalDateTime.now())
                .score(countCorrect.intValue())
                .build());

        log.info("RESPUESTAS DEL CUESTIONARIO EVALUADAS");

    }

}
