package com.manitas.domain.service.impl;

import com.manitas.application.dto.request.UserRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.UserResponseDto;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.data.repository.UserRepository;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.CatalogService;
import com.manitas.domain.service.UserService;
import com.manitas.utils.PageUtility;
import com.manitas.utils.UserUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.manitas.utils.Constants.*;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogService catalogService;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) throws BusinessException {

        validateMandatoryUserData(userRequestDto);

        if(Objects.isNull(getUserByEmail(userRequestDto))) {
            UserEntity userEntity = UserEntity.builder()
                    .idUser(UUID.randomUUID().toString())
                    .email(userRequestDto.getEmail())
                    .creationDate(LocalDateTime.now())
                    .name(userRequestDto.getName())
                    .lastname(userRequestDto.getLastname())
                    .secondLastname(userRequestDto.getSecondLastname())
                    .password(userRequestDto.getPassword())
                    .idRole(catalogService.getRoleById(userRequestDto.getIdRole()))
                    .idUserStatus(catalogService.getUserStatusById(userRequestDto.getIdUserStatus()))
                    .build();
            return UserUtility.entityToDto(userRepository.save(userEntity));
        }

        throw new BusinessException(USER + SPACE + ALREADY_EXISTS);

    }

    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto) throws BusinessException {

        validateMandatoryUserData(userRequestDto);
        validateOptionalUserData(userRequestDto);

        UserEntity userEntity = getUserById(userRequestDto);

        if(Objects.isNull(userEntity)) throw new BusinessException(USER + SPACE + NOT_FOUND);

        UserEntity userEntity1 = getUserByEmail(userRequestDto);

        if(Objects.nonNull(userEntity1) && !userEntity.getIdUser().equals(userEntity1.getIdUser()))
            throw new BusinessException(USER + SPACE + REQUIRED + SPACE + ALREADY_EXISTS);

        updateData(userEntity, userRequestDto);

        return UserUtility.entityToDto(userRepository.save(userEntity));

    }

    @Override
    public UserResponseDto getUser(String idUser) throws BusinessException {

        Optional<UserEntity> userEntity = userRepository.findUserByIdUser(idUser);
        if(userEntity.isPresent()) return UserUtility.entityToDto(userEntity.get());
        else throw new BusinessException(USER + SPACE + NOT_FOUND);

    }

    @Override
    public Page<UserResponseDto> getList(RequestDto<UserRequestDto> userRequestDtoRequestDto){

        return userRepository.findPageDtoByFilter(
                userRequestDtoRequestDto.getData().getEmail(),
                userRequestDtoRequestDto.getData().getName(),
                userRequestDtoRequestDto.getData().getLastname(),
                userRequestDtoRequestDto.getData().getSecondLastname(),
                userRequestDtoRequestDto.getData().getCreationDate(),
                userRequestDtoRequestDto.getData().getModificationDate(),
                userRequestDtoRequestDto.getData().getLastLogin(),
                userRequestDtoRequestDto.getData().getIdRole(),
                userRequestDtoRequestDto.getData().getIdUserStatus(),
                PageUtility.getPage(userRequestDtoRequestDto));

    }

    @Override
    public UserEntity getUserByEmail(String email) throws BusinessException {

        Optional<UserEntity> userEntity = userRepository.findUserByEmail(email);
        if(userEntity.isPresent()) return userEntity.get();
        else throw new BusinessException(USER + SPACE + NOT_FOUND);

    }

    private void updateData(UserEntity userEntity, UserRequestDto userRequestDto){

        boolean result = !userEntity.getName().equals(userRequestDto.getName())
                || !userEntity.getLastname().equals(userRequestDto.getLastname())
                || !userEntity.getEmail().equals(userRequestDto.getEmail())
                || !userEntity.getSecondLastname().equals(userRequestDto.getSecondLastname())
                || !userEntity.getIdUserStatus().getIdUserStatus().equals(userRequestDto.getIdUserStatus())
                || !userEntity.getIdRole().getIdRole().equals(userRequestDto.getIdRole());

        if(result){
            userEntity.setName(userRequestDto.getName());
            userEntity.setLastname(userRequestDto.getLastname());
            userEntity.setSecondLastname(userRequestDto.getSecondLastname());
            userEntity.setEmail(userRequestDto.getEmail());
            userEntity.setIdUserStatus(catalogService.getUserStatusById(userRequestDto.getIdUserStatus()));
            userEntity.setIdRole(catalogService.getRoleById(userRequestDto.getIdRole()));
            userEntity.setModificationDate(LocalDateTime.now());
        }

    }

    private void validateMandatoryUserData(UserRequestDto userRequestDto) throws BusinessException {

        if(Objects.isNull(userRequestDto)
                || Objects.isNull(userRequestDto.getEmail()) || Objects.isNull(userRequestDto.getLastname())
                || Objects.isNull(userRequestDto.getName()) || Objects.isNull(userRequestDto.getPassword()))
            throw new BusinessException(SOME + SPACE + USER + SPACE + REQUIRED);

    }

    private void validateOptionalUserData(UserRequestDto userRequestDto) throws BusinessException {

        if(Objects.isNull(userRequestDto) || Objects.isNull(userRequestDto.getIdUserStatus())
                || Objects.isNull(userRequestDto.getIdRole()))
            throw new BusinessException(SOME + SPACE + USER + SPACE + REQUIRED);

    }

    private UserEntity getUserByEmail(UserRequestDto userRequestDto){

        Optional<UserEntity> optionalUserEntity = userRepository.findUserByEmail(userRequestDto.getEmail());
        return optionalUserEntity.orElse(null);

    }

    private UserEntity getUserById(UserRequestDto userRequestDto){

        Optional<UserEntity> optionalUserEntity = userRepository.findUserByIdUser(userRequestDto.getIdUser());
        return optionalUserEntity.orElse(null);

    }

}
