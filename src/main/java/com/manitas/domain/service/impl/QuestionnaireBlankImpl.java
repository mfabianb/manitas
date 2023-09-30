package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.*;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.data.entity.*;
import com.manitas.domain.data.repository.QuestionnaireAnsweredRepository;
import com.manitas.domain.data.repository.QuestionnaireBlankRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.InterpellationService;
import com.manitas.domain.service.QuestionnaireBlankService;
import com.manitas.domain.service.QuestionnaireService;
import com.manitas.utils.QuestionnaireBlankUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.manitas.utils.Constants.*;

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
    public void createQuestionnaireAndInterpellations(QuestionnaireManualBlankDto questionnaireManualBlankDto) throws BusinessException {

        QuestionnaireEntity questionnaireEntity = questionnaireService.createQuestionnaireData(questionnaireManualBlankDto.getQuestionnaire());

        List<InterpellationEntity> interpellationEntities = new ArrayList<>();

        questionnaireManualBlankDto.getInterpellations().forEach(interpellationRequestDto -> {
            try {
                interpellationEntities.addAll(interpellationService.createInterpellation(interpellationRequestDto));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        });

        List<QuestionnaireBlankEntity> questionnaireBlankEntities = new ArrayList<>();

        String unique = UUID.randomUUID().toString();
        log.info("Generated unique: {}", unique);

        interpellationEntities.forEach(i ->
            questionnaireBlankEntities.add(QuestionnaireBlankEntity.builder()
                    .idQuestionnaireBlank(UUID.randomUUID().toString())
                    .idQuestionnaire(questionnaireEntity)
                    .idInterpellation(i)
                    .blankKey(unique)
                    .interpellationKey(i.getInterpellationKey())
                    .build()));

        String topics = getTopicListByInterpellationList(interpellationEntities);

        questionnaireEntity.setEnable(Boolean.TRUE);
        questionnaireEntity.setModificationDate(LocalDateTime.now());
        questionnaireEntity.setLength(questionnaireBlankEntities.size()/4);
        questionnaireEntity.setTopic(topics);

        List<QuestionnaireBlankEntity> questionnaireBlankEntity = questionnaireBlankRepository.saveAll(questionnaireBlankEntities);

        log.info("questionnaireBlankEntity.size(): {}", questionnaireBlankEntity.size());

        log.info("Se ha guardado el cuestionario por responder");

    }

    @Override
    public void createQuestionnaireSteady(QuestionnaireSteadyRequestDto questionnaireDto) throws BusinessException {

        String uniqueKey = UUID.randomUUID().toString();

        log.info("SE VA A CREAR EL CUESTIONARIO EN BLANCO CON EL ID: {}", uniqueKey);

        QuestionnaireEntity questionnaireEntity = questionnaireService.getQuestionnaireById(questionnaireDto.getIdQuestionnaire());

        if(Objects.nonNull(questionnaireEntity.getLength())
                && Objects.requireNonNull(questionnaireEntity.getLength()) > 0)
            throw new BusinessException("EL CUESTIONARIO YA HA CONTIENE REACTIVOS");

        List<InterpellationEntity> interpellationEntityList = new ArrayList<>();
        List<QuestionnaireBlankEntity> questionnaireBlankEntityList = new ArrayList<>();

        questionnaireDto.getIdInterpellations().forEach(q->{
            try {
                interpellationEntityList.addAll(interpellationService.getAllInterpellationByKey(q));
            } catch (BusinessException e) {
                log.info("ERROR AL AGREGAR UN REACTIVO: " + q + "," + ": " + e.getMessage());
            }
        });

        log.info("interpellationEntityList.size(): {}", interpellationEntityList.size());

        interpellationEntityList.forEach(i->
            questionnaireBlankEntityList.add(QuestionnaireBlankEntity.builder()
                    .idQuestionnaireBlank(UUID.randomUUID().toString())
                    .idQuestionnaire(questionnaireEntity)
                    .idInterpellation(i)
                    .interpellationKey(i.getInterpellationKey())
                    .blankKey(uniqueKey)
                    .build()));

        questionnaireBlankRepository.saveAll(questionnaireBlankEntityList);

        String topics = getTopicListByInterpellationList(interpellationEntityList);

        questionnaireEntity.setTopic(topics);
        questionnaireEntity.setModificationDate(LocalDateTime.now());
        questionnaireEntity.setLength(questionnaireBlankEntityList.size()/4);
        questionnaireEntity.setEnable(Boolean.TRUE);

        questionnaireService.updateQuestionnaireData(questionnaireEntity);

        log.info("SE CREÓ EL CUESTIONARIO EN BLANCO CON EL ID: {}", uniqueKey);

    }

    @Override
    public void createQuestionnaireDynamic(QuestionnaireDynamicBlankDto questionnaireDto) throws BusinessException {

        String uniqueKey = UUID.randomUUID().toString();

        log.info("SE VA A CREAR EL CUESTIONARIO EN BLANCO CON EL ID: {}", uniqueKey);

        QuestionnaireEntity questionnaireEntity = questionnaireService.getQuestionnaireById(questionnaireDto.getIdQuestionnaire());

        if(Objects.nonNull(questionnaireEntity.getLength())
                && Objects.requireNonNull(questionnaireEntity.getLength()) > 0)
            throw new BusinessException("EL CUESTIONARIO YA HA CONTIENE REACTIVOS");

        if(questionnaireDto.getInterpellationLimit() < 1) throw new BusinessException("EL CUESTIONARIO DEBE DE TENER AL MENOS UN REACTIVO");

        if(questionnaireDto.getTopics().isEmpty()) throw new BusinessException("EL CUESTIONARIO DEBE DE TENER AL MENOS UN TEMA");

        List<QuestionnaireBlankEntity> questionnaireBlankEntityList = new ArrayList<>();

        List<InterpellationEntity> interpellationEntityList = new ArrayList<>(interpellationService.getAllInterpellationByTopics(questionnaireDto));

        log.info("interpellationEntityList.size(): {}", interpellationEntityList.size());

        interpellationEntityList.forEach(i->
                questionnaireBlankEntityList.add(QuestionnaireBlankEntity.builder()
                        .idQuestionnaireBlank(UUID.randomUUID().toString())
                        .idQuestionnaire(questionnaireEntity)
                        .idInterpellation(i)
                        .interpellationKey(i.getInterpellationKey())
                        .blankKey(uniqueKey)
                        .build()));

        questionnaireBlankRepository.saveAll(questionnaireBlankEntityList);

        String topics = getTopicListByInterpellationList(interpellationEntityList);

        questionnaireEntity.setTopic(topics);
        questionnaireEntity.setModificationDate(LocalDateTime.now());
        questionnaireEntity.setLength(questionnaireBlankEntityList.size()/4);
        questionnaireEntity.setEnable(Boolean.TRUE);

        questionnaireService.updateQuestionnaireData(questionnaireEntity);

        log.info("SE CREÓ EL CUESTIONARIO EN BLANCO CON EL ID: {}", uniqueKey);

    }

    @Override
    public List<InterpellationResponseDto> getAllInterpellationToReplyDto(String blankKey){

        List<QuestionnaireBlankEntity> questionnaireBlankEntities = questionnaireBlankRepository.getInterpellationsByBlankKey(blankKey);

        return QuestionnaireBlankUtility.entityListToResponseDtoList(questionnaireBlankEntities);

    }

    @Override
    public QuestionnaireBlankEntity getBlankById(String id) throws BusinessException {

        Optional<QuestionnaireBlankEntity> optionalQuestionnaireBlankEntity = questionnaireBlankRepository.findBlankById(id);
        if(optionalQuestionnaireBlankEntity.isPresent()) return optionalQuestionnaireBlankEntity.get();
        else throw new BusinessException(USER + SPACE + NOT_FOUND);

    }

    @Override
    public long countInterpellationForQuestionnaireByBlankKey(String blankKey){
        return questionnaireBlankRepository.countInterpellationsBlankByBlankKey(blankKey) / 4;
    }

    private String getTopicListByInterpellationList(List<InterpellationEntity> interpellationEntities){

        StringBuilder stringBuilder = new StringBuilder();
        Set<String> topics = new HashSet<>();
        interpellationEntities.forEach(i -> topics.add(i.getIdQuestion().getIdTopic().getName()));
        topics.forEach(t -> stringBuilder.append(t).append(","));
        return stringBuilder.substring(0, stringBuilder.toString().length() - 1);

    }

}
