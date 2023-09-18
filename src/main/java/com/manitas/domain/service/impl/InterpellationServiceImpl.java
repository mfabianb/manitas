package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.AnswerRequestDto;
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
    public InterpellationEntity createInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException {

        validateCorrectAnswer(interpellationRequestDto.getAnswers());

        List<InterpellationEntity> interpellationEntityList = new ArrayList<>();

        List<InterpellationEntity> result = new ArrayList<>();

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
            result.addAll(interpellationRepository.saveAll(interpellationEntityList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Interpellation with id {} saved", unique);

        return result.get(0);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException {

        validateCorrectAnswer(interpellationRequestDto.getAnswers());

        List<InterpellationEntity> interpellationEntityList = getAllInterpellationById(interpellationRequestDto.getIdInterpellation());

        interpellationRequestDto.getQuestion().setIdQuestion(interpellationEntityList.get(0).getIdQuestion().getIdQuestion());

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

    private void validateCorrectAnswer(List<AnswerRequestDto> answerRequestDto) throws BusinessException {

        int correct = 0;
        int answers = 0;
        for(AnswerRequestDto a: answerRequestDto){
            answers++;
            if(Boolean.TRUE.equals(a.getCorrect())) correct++;
        }
        if(correct != 1 && answers != 4) throw new BusinessException(INTERPELLATION  + " NEEDS TO HAVE ONLY ONE CORRECT ANSWER, AND FOUR POSSIBLE ANSWERS");

    }

}
