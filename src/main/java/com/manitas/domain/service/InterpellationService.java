package com.manitas.domain.service;

import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.application.dto.request.QuestionnaireDynamicBlankDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InterpellationService {
    List<InterpellationEntity> createInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    InterpellationResponseDto updateInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException;

    InterpellationResponseDto getInterpellationResponseDtoByKey(String id) throws BusinessException;

    Page<InterpellationResponseDto> getAllInterpellationDto(RequestDto<InterpellationRequestDto> requestDto);

    List<InterpellationEntity> getAllInterpellationByKey(String id) throws BusinessException;

    List<InterpellationEntity> getAllInterpellationByTopics(
            QuestionnaireDynamicBlankDto questionnaireDynamicBlankDto) throws BusinessException;
}
