package com.manitas.application.controller;

import com.manitas.application.dto.response.AuthenticationResponseDto;
import com.manitas.domain.service.AuthenticationService;
import com.manitas.domain.service.AuthorizationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> login(@RequestHeader("SLX41L2L") String SLX41L2L,
                                                           @RequestHeader("XU8016Y8") String XU8016Y8){
        log.info("SLX41L2L: {}", SLX41L2L);
        log.info("XU8016Y8: {}", XU8016Y8);
        try{
            return new ResponseEntity<>(authenticationService.login(SLX41L2L, XU8016Y8), HttpStatus.OK);
        }catch (Exception businessException){
            log.info(businessException);
            return new ResponseEntity<>(AuthenticationResponseDto.builder().build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{key}")
    public ResponseEntity<Void> logout(@PathVariable(value="key") String key){
        try{
            authenticationService.logout(key);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception businessException){
            log.info(businessException);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/test")
    public ResponseEntity<Void> logtest(){
        try{
            authorizationService.getAuth();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception businessException){
            log.info(businessException);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
