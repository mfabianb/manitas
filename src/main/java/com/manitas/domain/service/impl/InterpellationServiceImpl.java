package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.data.entity.QuestionEntity;
import com.manitas.domain.data.repository.InterpellationRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.AnswerService;
import com.manitas.domain.service.InterpellationService;
import com.manitas.domain.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class InterpellationServiceImpl implements InterpellationService {

    @Autowired
    private InterpellationRepository interpellationRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException {

        List<InterpellationEntity> interpellationEntityList = new ArrayList<>();

        String unique = UUID.randomUUID().toString();
        log.info("Generated unique: {}", unique);

        QuestionEntity questionEntity = questionService.createQuestion(interpellationRequestDto.getQuestion());

        interpellationRequestDto.getAnswers().forEach(a->{
            try {
                log.info("To insert unique: {}", unique);
                interpellationEntityList.add(InterpellationEntity.builder()
                        .idInterpellation(unique)
                        .enable(Boolean.FALSE)
                        .idQuestion(questionEntity)
                        .idAnswer(answerService.createAnswer(a))
                        .correct(a.getCorrect())
                        .build());
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        });

        try {
            interpellationRepository.saveAll(interpellationEntityList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Guardado");

    }

}
