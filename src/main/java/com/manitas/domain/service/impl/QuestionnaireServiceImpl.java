package com.manitas.domain.service.impl;

import com.manitas.domain.data.entity.QuestionnaireEntity;
import com.manitas.domain.data.repository.QuestionnaireRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.QuestionnaireService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.manitas.utils.Constants.*;

@Service
@Log4j2
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Override
    public QuestionnaireEntity createQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException {

        validateMandatory(questionnaireEntity);

        return questionnaireRepository.save(
                QuestionnaireEntity.builder()
                        .idQuestionnaire(UUID.randomUUID().toString())
                        .name(questionnaireEntity.getName())
                        .description(questionnaireEntity.getDescription())
                        .creationDate(LocalDateTime.now())
                        .enable(Boolean.TRUE)
                        .build()
        );

    }

    @Override
    public QuestionnaireEntity updateQuestionnaireData(QuestionnaireEntity questionnaireEntity) throws BusinessException {

        validateMandatory(questionnaireEntity);

        QuestionnaireEntity questionnaireEntity1 = getQuestionnaireById(questionnaireEntity.getIdQuestionnaire());

        updateData(questionnaireEntity1, questionnaireEntity);

        return questionnaireRepository.save(questionnaireEntity1);

    }

    public void validateMandatory(QuestionnaireEntity questionnaireEntity) throws BusinessException {
        if(Objects.isNull(questionnaireEntity) || Objects.isNull(questionnaireEntity.getIdQuestionnaire())
                || Objects.isNull(questionnaireEntity.getDescription()))
            throw new BusinessException(SOME + QUESTIONNAIRE + SPACE + REQUIRED);
    }

    public QuestionnaireEntity getQuestionnaireById(String id) throws BusinessException {
        Optional<QuestionnaireEntity> optionalQuestionnaireEntity = questionnaireRepository.findById(id);
        if(optionalQuestionnaireEntity.isPresent()) return optionalQuestionnaireEntity.get();
        else throw new BusinessException(QUESTIONNAIRE + SPACE + NOT_FOUND);
    }

    public void updateData(QuestionnaireEntity saved, QuestionnaireEntity request){

        boolean result = !saved.getName().equals(request.getName()) || !saved.getDescription().equals(request.getDescription())
                || !saved.getEnable().equals(request.getEnable());

        if(result){
            saved.setName(request.getName());
            saved.setDescription(request.getDescription());
            saved.setEnable(request.getEnable());
            saved.setModificationDate(LocalDateTime.now());
        }

    }

}
