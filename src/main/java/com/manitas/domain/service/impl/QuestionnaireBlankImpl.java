package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.QuestionnaireBlankDto;
import com.manitas.domain.data.entity.*;
import com.manitas.domain.data.repository.QuestionnaireBlankRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.InterpellationService;
import com.manitas.domain.service.QuestionnaireBlankService;
import com.manitas.domain.service.QuestionnaireService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class QuestionnaireBlankImpl implements QuestionnaireBlankService {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private InterpellationService interpellationService;

    @Autowired
    private QuestionnaireBlankRepository questionnaireBlankRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createQuestionnaire(QuestionnaireBlankDto questionnaireBlankDto) throws BusinessException {

        QuestionnaireEntity questionnaireEntity = questionnaireService.createQuestionnaireData(questionnaireBlankDto.getQuestionnaire());

        List<InterpellationEntity> interpellationEntities = new ArrayList<>();

        questionnaireBlankDto.getInterpellations().forEach(interpellationRequestDto -> {
            try {
                interpellationEntities.add(interpellationService.createInterpellation(interpellationRequestDto));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        });

        List<QuestionnaireBlankEntity> questionnaireBlankEntities = new ArrayList<>();

        interpellationEntities.forEach(i -> {
            questionnaireBlankEntities.add(QuestionnaireBlankEntity.builder()
                    .idQuestionnaireBlank(UUID.randomUUID().toString())
                    .idQuestionnaire(questionnaireEntity)
                    .idInterpellation(i)
                    .build());
        });

        questionnaireBlankRepository.saveAll(questionnaireBlankEntities);

        log.info("Se ha guardado el cuestionario por responder");

    }

}
