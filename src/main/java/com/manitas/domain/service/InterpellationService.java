package com.manitas.domain.service;

import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InterpellationService {
    InterpellationEntity createInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void updateInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException;

    List<InterpellationEntity> getAllInterpellationById(String id) throws BusinessException;

    InterpellationResponseDto getInterpellationResponseDtoById(String id) throws BusinessException;

    Page<InterpellationResponseDto> getAllInterpellationDto(RequestDto<InterpellationRequestDto> requestDto);
}
