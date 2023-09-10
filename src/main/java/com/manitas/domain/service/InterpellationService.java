package com.manitas.domain.service;

import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.domain.exception.BusinessException;

public interface InterpellationService {
    void createInterpellation(InterpellationRequestDto interpellationRequestDto) throws BusinessException;
}
