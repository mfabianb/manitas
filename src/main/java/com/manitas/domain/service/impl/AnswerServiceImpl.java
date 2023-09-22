package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.AnswerRequestDto;
import com.manitas.domain.data.entity.AnswerEntity;
import com.manitas.domain.data.entity.MediaEntity;
import com.manitas.domain.data.repository.AnswerRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.AnswerService;
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
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AnswerEntity createAnswer(AnswerRequestDto answerRequestDto) throws BusinessException {

        validateMandatoryAnswerDto(answerRequestDto);

        String uniqueKey = UUID.randomUUID().toString();

        return answerRepository.saveAndFlush(AnswerEntity.builder()
                        .idAnswer(uniqueKey)
                        .answer(answerRequestDto.getAnswer())
                        .idMedia(MediaEntity.builder()
                                .idMedia(UUID.randomUUID().toString())
                                .media(answerRequestDto.getMedia().getMedia())
                                .source(answerRequestDto.getMedia().getSource())
                                .type(answerRequestDto.getMedia().getType())
                                .build())
                        .creationDate(LocalDateTime.now())
                        .enable(Boolean.TRUE)
                .build());

    }

    @Override
    public AnswerEntity updateAnswer(AnswerRequestDto answerRequestDto) throws BusinessException {

        validateMandatoryAnswerDto(answerRequestDto);

        AnswerEntity answerEntity = getAnswerById(answerRequestDto.getIdAnswer());

        updateData(answerRequestDto, answerEntity);

        return answerRepository.saveAndFlush(answerEntity);

    }

    @Override
    public AnswerEntity getAnswerById(String id) throws BusinessException {
        Optional<AnswerEntity> optionalAnswerEntity = answerRepository.findById(id);
        if(optionalAnswerEntity.isPresent()) return optionalAnswerEntity.get();
        else throw new BusinessException(SOME + ANSWER + SPACE + REQUIRED);
    }

    private void updateData(AnswerRequestDto answerRequestDto, AnswerEntity answerEntity){
        answerEntity.setAnswer(answerRequestDto.getAnswer());
        answerEntity.setEnable(answerRequestDto.getEnable());
        answerEntity.setModificationDate(LocalDateTime.now());

        MediaEntity media = answerEntity.getIdMedia();
        media.setMedia(answerRequestDto.getMedia().getMedia());
        media.setSource(answerRequestDto.getMedia().getSource());
        media.setType(answerRequestDto.getMedia().getType());

        answerEntity.setIdMedia(media);
    }

    private void validateMandatoryAnswerDto(AnswerRequestDto answerRequestDto) throws BusinessException {
        if(Objects.isNull(answerRequestDto) || Objects.isNull(answerRequestDto.getAnswer()) || Objects.isNull(answerRequestDto.getMedia())
                || Objects.isNull(answerRequestDto.getMedia().getType()))
            throw new BusinessException(SOME + ANSWER + SPACE + REQUIRED);
    }

}
