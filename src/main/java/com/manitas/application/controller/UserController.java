package com.manitas.application.controller;

import com.manitas.application.dto.UserRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public DataResponse<UserEntity> createUser(@RequestBody UserRequestDto userRequestDto){
        log.info(userRequestDto);
        try{
            return new DataResponse<>(true, null, HttpStatus.OK.value(), userService.createUser(userRequestDto));
        } catch (BusinessException e) {
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

    @PutMapping("/{idUser}")
    public DataResponse<UserEntity> updateUser(@PathVariable(value="idUser") String idUser, @RequestBody UserRequestDto userRequestDto){
        userRequestDto.setIdUser(idUser);
        log.info(userRequestDto);
        try{
            return new DataResponse<>(true, null, HttpStatus.OK.value(), userService.updateUser(userRequestDto));
        } catch (BusinessException e) {
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

    @GetMapping("/{idUser}")
    public DataResponse<UserEntity> getUser(@PathVariable(value="idUser") String idUser){
        try{
            return new DataResponse<>(true, null, HttpStatus.OK.value(), userService.getUser(idUser));
        } catch (BusinessException e) {
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

    @GetMapping
    public DataResponse<Page<UserEntity>> getList(@RequestBody RequestDto<UserEntity> requestDto){
        return new DataResponse<>(true, null, HttpStatus.OK.value(), userService.getList(requestDto));
    }
}
