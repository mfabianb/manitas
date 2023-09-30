package com.manitas.domain.service.impl;

import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.AuthorizationService;
import com.manitas.domain.service.EncodeService;
import com.manitas.domain.service.UserService;
import com.manitas.utils.ServletUtility;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.manitas.utils.Constants.UNAUTHORIZED;


@Service
@Log4j2
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    UserService userService;

    @Autowired
    EncodeService encodeService;

    @Override
    public UserEntity getAuth() throws BusinessException {
        String token = ServletUtility.getToken();

        if(token.isEmpty()) throw new BusinessException(UNAUTHORIZED);

        String tokenData = encodeService.decode(token);

        if(Objects.isNull(tokenData)) throw new BusinessException(UNAUTHORIZED);

        UserEntity userEntity = userService.getUserEntity(tokenData);

        if(userEntity.getIdUserStatus().getIdUserStatus() != 101 || Boolean.FALSE.equals(userEntity.getTokenEnable())
                || !userEntity.getToken().equals(token))
            throw new BusinessException(UNAUTHORIZED);

        log.info("Authorization successful");

        return userEntity;

    }

}
