package com.manitas.application.controller;

import com.manitas.application.dto.request.UserRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.application.dto.response.UserResponseDto;
import com.manitas.domain.data.entity.UserEntity;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<DataResponse<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto){
        HttpStatus httpStatus;
        log.info(userRequestDto);
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), userService.createUser(userRequestDto)), httpStatus);
        } catch (BusinessException e) {
            log.info(e);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null), httpStatus);
        }
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<DataResponse<UserResponseDto>> updateUser(@PathVariable(value="idUser") String idUser, @RequestBody UserRequestDto userRequestDto){
        HttpStatus httpStatus;
        userRequestDto.setIdUser(idUser);
        log.info(userRequestDto);
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), userService.updateUser(userRequestDto)), httpStatus);
        } catch (BusinessException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null), httpStatus);
        }
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<DataResponse<UserResponseDto>> getUser(@PathVariable(value="idUser") String idUser){
        HttpStatus httpStatus;
        log.info("getUser by id {}", idUser);
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), userService.getUser(idUser)), httpStatus);
        } catch (BusinessException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null), httpStatus);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<UserResponseDto>>> getList(@RequestBody RequestDto<UserRequestDto> requestDto){
        HttpStatus httpStatus;
        log.info(requestDto);
        try{
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(), userService.getList(requestDto)), httpStatus);
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.info(e);
            return new ResponseEntity<>(new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null), httpStatus);
        }
    }
}
