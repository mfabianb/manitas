package com.manitas.utils;

import com.manitas.application.dto.response.AnswerResponseDto;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.data.entity.AnswerEntity;
import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.data.entity.QuestionnaireBlankEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionnaireBlankUtility {

    private QuestionnaireBlankUtility(){/*Comment to avoid Sonar lint*/}

    public static List<InterpellationResponseDto> entityListToResponseDtoList(List<QuestionnaireBlankEntity> questionnaireBlankEntityList){
        Set<String> set = new HashSet<>();

        questionnaireBlankEntityList.forEach(q->set.add(q.getIdInterpellation().getInterpellationKey()));

        List<InterpellationResponseDto> interpellationResponseDtoList = new ArrayList<>();

        set.forEach(s ->
                interpellationResponseDtoList.add(entityToResponseDto( questionnaireBlankEntityList.stream().filter(q->q.getInterpellationKey().equals(s)).collect(Collectors.toList()) ))
        );

        return interpellationResponseDtoList;

    }

    public static InterpellationResponseDto entityToResponseDto(List<QuestionnaireBlankEntity> questionnaireBlankEntities){

        InterpellationResponseDto response = new InterpellationResponseDto();

        response.setIdInterpellation(questionnaireBlankEntities.get(0).getIdInterpellation().getIdInterpellation());
        response.setQuestion(questionnaireBlankEntities.get(0).getIdInterpellation().getIdQuestion());
        response.setInterpellationKey(questionnaireBlankEntities.get(0).getIdInterpellation().getInterpellationKey());
        response.setIdQuestionnaireBlank(questionnaireBlankEntities.get(0).getIdQuestionnaireBlank());
        response.setQuestionnaireBlankKey(questionnaireBlankEntities.get(0).getBlankKey());
        response.setAnswers(new ArrayList<>());

        questionnaireBlankEntities.forEach(q ->
                response.getAnswers().add(entityToResponseDto(q.getIdInterpellation().getIdAnswer()))
        );

        return response;
    }

    private static AnswerResponseDto entityToResponseDto(AnswerEntity answerEntity){
        return AnswerResponseDto.builder()
                .idAnswer(answerEntity.getIdAnswer())
                .answer(answerEntity.getAnswer())
                .correct(Boolean.FALSE)
                .creationDate(answerEntity.getCreationDate())
                .enable(answerEntity.getEnable())
                .idMedia(answerEntity.getIdMedia())
                .build();
    }

}
