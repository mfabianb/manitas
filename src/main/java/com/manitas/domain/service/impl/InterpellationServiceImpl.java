package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.AnswerRequestDto;
import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.domain.data.entity.AnswerEntity;
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

import java.util.*;

import static com.manitas.utils.Constants.*;

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

        log.info("Interpellation with id {} saved", unique);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException {

        List<InterpellationEntity> interpellationEntityList = getAllInterpellationById(interpellationRequestDto.getIdInterpellation());
        QuestionEntity questionEntity = questionService.updateQuestion(interpellationRequestDto.getQuestion());

        interpellationEntityList.forEach(i->{
            try {

                Optional<AnswerRequestDto> answerRequestDto = interpellationRequestDto.getAnswers().stream().filter(
                        a -> a.getIdAnswer().equals(i.getIdAnswer().getIdAnswer())
                ).findFirst();

                if(answerRequestDto.isPresent()){
                    updateData(interpellationRequestDto, i);
                    i.setIdQuestion(questionEntity);
                    i.setIdAnswer(answerService.updateAnswer(answerRequestDto.get()));
                    i.setCorrect(answerRequestDto.get().getCorrect());
                }

            } catch (BusinessException e) {
                e.printStackTrace();
            }
        });

        try {
            interpellationRepository.saveAll(interpellationEntityList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Interpellation list with id {} saved", interpellationEntityList.get(0).getIdInterpellation());

    }

    public InterpellationEntity getInterpellationById(String id) throws BusinessException {
        Optional<InterpellationEntity> optionalInterpellationEntity = interpellationRepository.findById(id);
        if(optionalInterpellationEntity.isPresent()) return optionalInterpellationEntity.get();
        else throw new BusinessException(SOME + INTERPELLATION + SPACE + REQUIRED);
    }

    public List<InterpellationEntity> getAllInterpellationById(String id) throws BusinessException {
        List<InterpellationEntity> optionalInterpellationEntity = interpellationRepository.getAllById(id);
        if(!optionalInterpellationEntity.isEmpty()) return optionalInterpellationEntity;
        else throw new BusinessException(SOME + INTERPELLATION + SPACE + REQUIRED);
    }

    private void updateData(InterpellationRequestDto request, InterpellationEntity entity){

        boolean result = !entity.getEnable().equals(request.getEnable());

        if(result){
            entity.setEnable(request.getEnable());
        }

    }

}
