package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.QuestionnaireBlankDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.domain.data.entity.*;
import com.manitas.domain.data.repository.QuestionnaireBlankRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.InterpellationService;
import com.manitas.domain.service.QuestionnaireBlankService;
import com.manitas.domain.service.QuestionnaireService;
import com.manitas.utils.PageUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        StringBuilder stringBuilder = new StringBuilder();

        interpellationEntities.forEach(i ->{
            stringBuilder.append(i.getIdQuestion().getIdTopic().getName()).append(",");
            questionnaireBlankEntities.add(QuestionnaireBlankEntity.builder()
                    .idQuestionnaireBlank(UUID.randomUUID().toString())
                    .idQuestionnaire(questionnaireEntity)
                    .idInterpellation(i)
                    .build());
        });

        questionnaireEntity.setEnable(Boolean.TRUE);
        questionnaireEntity.setModificationDate(LocalDateTime.now());
        questionnaireEntity.setLength(questionnaireBlankEntities.size());
        questionnaireEntity.setTopic(stringBuilder.substring(0, stringBuilder.toString().length() - 1));

        questionnaireBlankRepository.saveAll(questionnaireBlankEntities);

        log.info("Se ha guardado el cuestionario por responder");

    }

    @Override
    public Page<InterpellationEntity> getInterpellationByQuestionnaire(String idQuestionnaire) throws BusinessException {

        RequestDto<String> requestDto = new RequestDto<>();
        requestDto.setData(idQuestionnaire);
        requestDto.setDescending(true);
        requestDto.setPage(0);
        requestDto.setSize(100);
        requestDto.setSort("creationDate");

        return questionnaireBlankRepository.getInterpellationsByIdQuestionnaire(
                questionnaireService.getQuestionnaireById(idQuestionnaire), PageUtility.getPage(requestDto));

    }

}
