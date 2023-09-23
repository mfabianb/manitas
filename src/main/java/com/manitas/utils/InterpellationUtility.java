package com.manitas.utils;

import com.manitas.application.dto.response.AnswerResponseDto;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.data.entity.AnswerEntity;
import com.manitas.domain.data.entity.InterpellationEntity;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class InterpellationUtility {

    private InterpellationUtility(){/*Comment to avoid Sonar lint*/}

    public static List<InterpellationResponseDto> entityListToResponseDtoList(List<InterpellationEntity> interpellationEntity){
        Set<String> set = new HashSet<>();

        interpellationEntity.forEach(i->set.add(i.getInterpellationKey()));

        List<InterpellationResponseDto> interpellationResponseDtoList = new ArrayList<>();

        set.forEach(s ->
            interpellationResponseDtoList.add(entityToResponseDto(interpellationEntity.stream().filter(i->i.getInterpellationKey().equals(s)).collect(Collectors.toList())))
        );

        return interpellationResponseDtoList;

    }

    public static InterpellationResponseDto entityToResponseDto(List<InterpellationEntity> interpellationEntity){

        InterpellationResponseDto response = new InterpellationResponseDto();

        response.setIdInterpellation(interpellationEntity.get(0).getIdInterpellation());
        response.setQuestion(interpellationEntity.get(0).getIdQuestion());
        response.setInterpellationKey(interpellationEntity.get(0).getInterpellationKey());
        response.setAnswers(new ArrayList<>());

        interpellationEntity.forEach(i->
                response.getAnswers().add(entityToResponseDto(i.getIdAnswer(), i.getCorrect()))
        );

        return response;
    }

    private static AnswerResponseDto entityToResponseDto(AnswerEntity answerEntity, Boolean correct){
        return AnswerResponseDto.builder()
                .idAnswer(answerEntity.getIdAnswer())
                .answer(answerEntity.getAnswer())
                .correct(correct)
                .creationDate(answerEntity.getCreationDate())
                .enable(answerEntity.getEnable())
                .idMedia(answerEntity.getIdMedia())
                .build();
    }

}
