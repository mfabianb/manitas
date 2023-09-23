package com.manitas.domain.service;

import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.exception.BusinessException;

public interface AuthorizationService {
    UserEntity getAuth() throws BusinessException;
}
