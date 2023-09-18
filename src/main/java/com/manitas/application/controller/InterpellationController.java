package com.manitas.application.controller;

import com.manitas.application.dto.request.InterpellationRequestDto;
import com.manitas.application.dto.request.RequestDto;
import com.manitas.application.dto.response.DataResponse;
import com.manitas.application.dto.response.InterpellationResponseDto;
import com.manitas.domain.exception.BusinessException;
import com.manitas.domain.service.InterpellationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/interpellation")
public class InterpellationController {

    @Autowired
    private InterpellationService interpellationService;

    @PostMapping
    public ResponseEntity<Object> createInterpellation(@RequestBody InterpellationRequestDto interpellationRequestDto){
        try{
            interpellationService.createInterpellation(interpellationRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (BusinessException e){
            log.info(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idInterpellation}")
    public ResponseEntity<Object> updateInterpellation(@PathVariable(value="idInterpellation") String idInterpellation,
                                                       @RequestBody InterpellationRequestDto interpellationRequestDto){
        try{
            interpellationRequestDto.setIdInterpellation(idInterpellation);
            interpellationService.updateInterpellation(interpellationRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (BusinessException e){
            log.info(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idInterpellation}")
    public ResponseEntity<DataResponse<InterpellationResponseDto>> getInterpellation(@PathVariable(value="idInterpellation") String idInterpellation){
        try{
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(),
                    interpellationService.getInterpellationResponseDtoById(idInterpellation)) , HttpStatus.OK);
        }catch (Exception e){
            log.info(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Page<InterpellationResponseDto>>> getInterpellationList(@RequestBody RequestDto<InterpellationRequestDto> requestDto){
        log.info(requestDto);
        try{
            return new ResponseEntity<>(new DataResponse<>(true, null, HttpStatus.OK.value(),
                    interpellationService.getAllInterpellationDto(requestDto)) , HttpStatus.OK);
        }catch (Exception e){
            log.info(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
