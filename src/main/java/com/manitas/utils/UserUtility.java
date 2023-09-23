package com.manitas.utils;

import com.manitas.application.dto.response.UserResponseDto;
import com.manitas.domain.data.entity.UserEntity;

public class UserUtility {

    private UserUtility(){/*Comment to avoid Sonar lint*/}

    public static UserResponseDto entityToDto(UserEntity userEntity){
        return UserResponseDto.builder()
                .idUser(userEntity.getIdUser())
                .email(userEntity.getEmail())
                .lastname(userEntity.getLastname())
                .name(userEntity.getName())
                .secondLastname(userEntity.getSecondLastname())
                .creationDate(userEntity.getCreationDate())
                .modificationDate(userEntity.getModificationDate())
                .lastLogin(userEntity.getLastLogin())
                .idUserStatus(userEntity.getIdUserStatus().getIdUserStatus())
                .nameStatus(userEntity.getIdUserStatus().getName())
                .idRole(userEntity.getIdRole().getIdRole())
                .nameRole(userEntity.getIdRole().getName())
                .build();
    }

}
