package com.manitas.domain.service.impl;

import com.manitas.application.dto.response.AuthenticationResponseDto;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.AuthenticationService;
import com.manitas.domain.service.EncodeService;
import com.manitas.domain.service.UserService;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.manitas.utils.Constants.UNAUTHORIZED;


@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserService userService;

    @Autowired
    EncodeService encodeService;

    @Override
    public AuthenticationResponseDto login(String username, String password) throws BusinessException {
        if(Objects.isNull(username) || Objects.isNull(password)) {
            throw new BusinessException(UNAUTHORIZED);
        }

        UserEntity userEntity = userService.getUserByCredentials(username, password);

        String loginData = userEntity.getIdUser();

        String encode = encodeService.encode(loginData);

        userEntity.setLastLogin(LocalDateTime.now());
        userEntity.setToken(encode);
        userEntity.setTokenEnable(Boolean.TRUE);

        userService.updateUserEntity(userEntity);

        return AuthenticationResponseDto.builder().token(encode).build();
    }

    @Override
    public void logout(String key) throws BusinessException {
        if(Objects.isNull(key)) throw new BusinessException(UNAUTHORIZED);

        UserEntity userEntity = userService.getUserEntity(key);

        userEntity.setTokenEnable(Boolean.FALSE);

        userService.updateUserEntity(userEntity);
    }

}
