package com.manitas.application.controller;

import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.InterpellationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/interpellation")
public class InterpellationController {

    @Autowired
    private InterpellationService interpellationService;

    @PostMapping
    public DataResponse<?> createInterpellation(@RequestBody InterpellationRequestDto interpellationRequestDto){
        try{
            interpellationService.createInterpellation(interpellationRequestDto);
            return new DataResponse<>(true, null, HttpStatus.OK.value(), null);
        }catch (BusinessException e){
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

    @PutMapping("/{idInterpellation}")
    public DataResponse<?> updateInterpellation(@PathVariable(value="idInterpellation") String idInterpellation,
                                                @RequestBody InterpellationRequestDto interpellationRequestDto){
        try{
            interpellationRequestDto.setIdInterpellation(idInterpellation);
            interpellationService.updateInterpellation(interpellationRequestDto);
            return new DataResponse<>(true, null, HttpStatus.OK.value(), null);
        }catch (BusinessException e){
            log.info(e);
            return new DataResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
    }

}
