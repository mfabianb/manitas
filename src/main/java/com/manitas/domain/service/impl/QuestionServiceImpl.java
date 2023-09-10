package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.QuestionRequestDto;
import com.manitas.domain.data.entity.MediaEntity;
import com.manitas.domain.data.entity.QuestionEntity;
import com.manitas.domain.data.repository.QuestionRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.CatalogService;
import com.manitas.domain.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.manitas.utils.Constants.*;

@Service
@Log4j2
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CatalogService catalogService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public QuestionEntity createQuestion(QuestionRequestDto questionRequestDto) throws BusinessException {

        validateMandatoryQuestionDto(questionRequestDto);

        return questionRepository.saveAndFlush(QuestionEntity.builder()
                .idQuestion(UUID.randomUUID().toString())
                .question(questionRequestDto.getQuestion())
                .idMedia(MediaEntity.builder()
                        .idMedia(UUID.randomUUID().toString())
                        .media(questionRequestDto.getMedia().getMedia())
                        .source(questionRequestDto.getMedia().getSource())
                        .type(questionRequestDto.getMedia().getType())
                        .build())
                .creationDate(LocalDateTime.now())
                .idTopic(catalogService.getTopicById(questionRequestDto.getIdTopic()))
                .enable(Boolean.FALSE)
                .build());

    }

    @Override
    public QuestionEntity updateQuestion(QuestionRequestDto questionRequestDto) throws BusinessException {

        validateMandatoryQuestionDto(questionRequestDto);

        QuestionEntity questionEntity = getQuestionById(questionRequestDto.getIdQuestion());

        updateData(questionRequestDto, questionEntity);

        return questionRepository.saveAndFlush(questionEntity);

    }

    @Override
    public QuestionEntity getQuestionById(String id) throws BusinessException {
        Optional<QuestionEntity> optionalQuestionEntity = questionRepository.findById(id);
        if(optionalQuestionEntity.isPresent()) return optionalQuestionEntity.get();
        else throw new BusinessException(SOME + QUESTION + SPACE + REQUIRED);
    }

    private void updateData(QuestionRequestDto questionRequestDto, QuestionEntity questionEntity){
        questionEntity.setQuestion(questionRequestDto.getQuestion());
        questionEntity.setEnable(questionRequestDto.getEnable());
        questionEntity.setModificationDate(LocalDateTime.now());

        MediaEntity media = questionEntity.getIdMedia();
        media.setMedia(questionRequestDto.getMedia().getMedia());
        media.setSource(questionRequestDto.getMedia().getSource());
        media.setType(questionRequestDto.getMedia().getType());

        questionEntity.setIdTopic(catalogService.getTopicById(questionRequestDto.getIdTopic()));
        questionEntity.setIdMedia(media);
    }

    private void validateMandatoryQuestionDto(QuestionRequestDto questionRequestDto) throws BusinessException {
        if(Objects.isNull(questionRequestDto) || Objects.isNull(questionRequestDto.getQuestion()) || Objects.isNull(questionRequestDto.getMedia())
                || Objects.isNull(questionRequestDto.getMedia().getType()) || Objects.isNull(questionRequestDto.getIdTopic()))
            throw new BusinessException(SOME + QUESTION + SPACE + REQUIRED);
    }
}
