package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.InterpellationReplyRequestDto;
import com.manitas.application.dto.request.ReplyQuestionnaireFromUserRequestDto;
import com.manitas.domain.data.entity.*;
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
import java.util.Objects;
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
    private InterpellationService interpellationService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void questionnaireReplyFromUser(ReplyQuestionnaireFromUserRequestDto replyDto) throws BusinessException {

        validateDto(replyDto);

        UserEntity userEntity = userService.getUserEntity(replyDto.getIdUser());

        List<QuestionnaireAnsweredEntity> questionnaireAnsweredEntityList = new ArrayList<>();

        long count = questionnaireBlankService.countInterpellationForQuestionnaireByBlankKey(replyDto.getBlankKey());
        String unique = UUID.randomUUID().toString();

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
                                .answeredKey(unique)
                                .build()
                );
            } catch (BusinessException e) {
                log.info(e);
            }
        });

        if(questionnaireAnsweredEntityList.size() != count) throw new BusinessException("EL NÚMERO DE RESPUESTAS GUARDADAS Y PREGUNTAS DEBE DE SER IGUAL");

        questionnaireAnsweredRepository.saveAll(questionnaireAnsweredEntityList);

        log.info("RESPUESTAS DEL CUESTIONARIO GUARDADAS");

        evaluateQuestionnaire(userEntity, replyDto.getBlankKey(), unique);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void evaluateQuestionnaire(UserEntity userEntity, String blankKey, String unique) throws BusinessException {

        List<QuestionnaireAnsweredEntity> questionnaireAnsweredEntityList =
                questionnaireAnsweredRepository.findByIdUserAndBlankKeyAndAnsweredKey(blankKey, unique, userEntity);

        if(questionnaireAnsweredEntityList.isEmpty()) throw new BusinessException("EL CUESTIONARIO DEBE DE ESTAR RESPONDIDO");

        QuestionnaireEntity questionnaireEntity = questionnaireAnsweredEntityList.get(0).getIdQuestionnaireBlank().getIdQuestionnaire();

        AtomicLong countCorrect = new AtomicLong();

        questionnaireAnsweredEntityList.forEach(q -> {

            try {
                if(Boolean.TRUE.equals(interpellationService.getAllInterpellationByKey(q.getIdQuestionnaireBlank().getInterpellationKey())
                        .stream().filter(i-> i.getIdAnswer().getIdAnswer().equals(q.getUserAnswer()))
                        .findFirst().orElse(new InterpellationEntity()).getCorrect()))
                    countCorrect.getAndIncrement();
            } catch (BusinessException e) {
                e.printStackTrace();
            }

        });

        userResultRepository.save(UserResultEntity.builder()
                .idUserResult(UUID.randomUUID().toString())
                .idUser(userEntity)
                .idQuestionnaire(questionnaireEntity)
                .creationDate(LocalDateTime.now())
                .score(countCorrect.intValue())
                .answeredKey(unique)
                .build());

        log.info("RESPUESTAS DEL CUESTIONARIO EVALUADAS");

    }

    private void validateDto(ReplyQuestionnaireFromUserRequestDto replyDto) throws BusinessException {

        if(Objects.isNull(replyDto) || Objects.isNull(replyDto.getBlankKey()) || replyDto.getBlankKey().isEmpty()
                || Objects.isNull(replyDto.getIdUser()) || replyDto.getIdUser().isEmpty())
            throw new BusinessException("LA INFORMACIÓN ES NECESARIA");

        for(InterpellationReplyRequestDto a: replyDto.getAnswers()){
            if(Objects.isNull(a) || Objects.isNull(a.getAnswer()) || a.getAnswer().isEmpty()
                    || Objects.isNull(a.getIdQuestionnaireBlank()) || a.getIdQuestionnaireBlank().isEmpty())
                throw new BusinessException("LA INFORMACIÓN ES NECESARIA");
        }

    }

}
