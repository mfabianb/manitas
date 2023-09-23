package com.manitas.domain.service;

import com.manitas.application.dto.request.UserRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.UserResponseDto;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.exception.BusinessException;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto) throws BusinessException;

    UserResponseDto updateUser(UserRequestDto userRequestDto) throws BusinessException;

    UserResponseDto getUser(String idUser) throws BusinessException;

    UserEntity updateUserEntity(UserEntity idUser) throws BusinessException;

    UserEntity getUserEntity(String idUser) throws BusinessException;

    Page<UserResponseDto> getList(RequestDto<UserRequestDto> userRequestDtoRequestDto);

    UserEntity getUserByEmail(String email) throws BusinessException;

    UserEntity getUserByCredentials(String username, String password) throws BusinessException;
}
