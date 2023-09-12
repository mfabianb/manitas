package com.manitas.domain.service;

import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.domain.exception.BusinessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface InterpellationService {
    void createInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void updateInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException;
}
