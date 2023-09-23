package com.manitas.domain.service;


import com.manitas.application.dto.response.AuthenticationResponseDto;
import com.manitas.domain.exception.BusinessException;

public interface AuthenticationService {
    AuthenticationResponseDto login(String username, String password) throws BusinessException;

    void logout(String key) throws BusinessException;
}
