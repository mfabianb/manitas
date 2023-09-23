package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.AnswerRequestDto;
import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.application.dto.request.QuestionnaireDynamicBlankDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.data.entity.QuestionEntity;
import com.manitas.domain.data.repository.InterpellationRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.AnswerService;
import com.manitas.domain.service.InterpellationService;
import com.manitas.domain.service.QuestionService;
import com.manitas.utils.InterpellationUtility;
import com.manitas.utils.PageUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<InterpellationEntity> createInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException {

        validateCorrectAnswer(interpellationRequestDto.getAnswers());

        List<InterpellationEntity> interpellationEntityList = new ArrayList<>();

        List<InterpellationEntity> result = new ArrayList<>();

        String unique = UUID.randomUUID().toString();
        log.info("Generated unique: {}", unique);

        QuestionEntity questionEntity = questionService.createQuestion(interpellationRequestDto.getQuestion());

        interpellationRequestDto.getAnswers().forEach(a->{
            try {
                interpellationEntityList.add(InterpellationEntity.builder()
                        .idInterpellation(UUID.randomUUID().toString())
                        .enable(Boolean.TRUE)
                        .idQuestion(questionEntity)
                        .idAnswer(answerService.createAnswer(a))
                        .correct(a.getCorrect())
                        .interpellationKey(unique)
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

        log.info("Interpellation saved");

        return result;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public InterpellationResponseDto updateInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException {

        validateCorrectAnswer(interpellationRequestDto.getAnswers());

        List<InterpellationEntity> interpellationEntityList = getAllInterpellationByKey(interpellationRequestDto.getInterpellationKey());

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

        List<InterpellationEntity> interpellationResult = new ArrayList<>();

        try {
            interpellationResult.addAll(interpellationRepository.saveAll(interpellationEntityList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Interpellation list with id {} saved", interpellationEntityList.get(0).getIdInterpellation());

        return InterpellationUtility.entityToResponseDto(interpellationResult);

    }

    @Override
    public InterpellationResponseDto getInterpellationResponseDtoByKey(String id) throws BusinessException {
        List<InterpellationEntity> optionalInterpellationEntity = interpellationRepository.getAllByInterpellationKey(id);
        if(!optionalInterpellationEntity.isEmpty()){
            return InterpellationUtility.entityToResponseDto(optionalInterpellationEntity);
        }
        else throw new BusinessException(INTERPELLATION + SPACE + NOT_FOUND);
    }

    @Override
    public Page<InterpellationResponseDto> getAllInterpellationDto(RequestDto<InterpellationRequestDto> requestDto){

        Page<InterpellationEntity> optionalInterpellationEntity = interpellationRepository.findAllEntityPage(requestDto.getData().getQuestion().getQuestion(),
                requestDto.getData().getQuestion().getCreationDate(), requestDto.getData().getQuestion().getMedia().getType(),
                Boolean.TRUE, requestDto.getData().getQuestion().getEnable(), PageUtility.getPage(requestDto));

        List<InterpellationResponseDto> interpellationResponseDtoList
                = new ArrayList<>(InterpellationUtility.entityListToResponseDtoList(optionalInterpellationEntity.getContent()));

        int totalSize = (interpellationResponseDtoList.isEmpty()) ? 1 : interpellationResponseDtoList.size();

        Pageable pageRequest = createPageRequestUsing(requestDto.getPage(), totalSize);
        return new PageImpl<>(interpellationResponseDtoList, pageRequest, interpellationResponseDtoList.size());

    }

    @Override
    public List<InterpellationEntity> getAllInterpellationByKey(String id) throws BusinessException {
        List<InterpellationEntity> optionalInterpellationEntity = interpellationRepository.findAllByInterpellationKey(id);
        if(!optionalInterpellationEntity.isEmpty()) return optionalInterpellationEntity;
        else throw new BusinessException(SOME + INTERPELLATION + SPACE + REQUIRED);
    }

    @Override
    public List<InterpellationEntity> getAllInterpellationByTopics(
            QuestionnaireDynamicBlankDto questionnaireDynamicBlankDto) throws BusinessException {

        RequestDto<String> requestDto = new RequestDto<>();
        requestDto.setData("");
        requestDto.setDescending(true);
        requestDto.setPage(0);
        requestDto.setSize(questionnaireDynamicBlankDto.getInterpellationLimit() * 4);
        requestDto.setSort("interpellationKey");
        Pageable pageable = PageUtility.getPage(requestDto);

        Page<InterpellationEntity> optionalInterpellationEntity
                = interpellationRepository.findAllByIdTopics(questionnaireDynamicBlankDto.getTopics(), pageable);
        if(!optionalInterpellationEntity.isEmpty()) return optionalInterpellationEntity.getContent();
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

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }


}
