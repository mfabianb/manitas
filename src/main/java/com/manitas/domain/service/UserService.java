package com.manitas.domain.service;

import com.manitas.application.dto.UserRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;

public interface UserService {
    UserEntity createUser(UserRequestDto userRequestDto) throws BusinessException;

    UserEntity updateUser(UserRequestDto userRequestDto) throws BusinessException;

    UserEntity getUser(String idUser) throws BusinessException;

    Page<UserEntity> getList(RequestDto<UserEntity> userRequestDtoRequestDto);
}
