package com.manitas.utils;

import com.manitas.application.dto.request.RequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtility {

    private PageUtility(){}

    public static Pageable getPage(RequestDto<?> requestDto){
        if(Boolean.TRUE.equals(requestDto.getDescending()))
            return PageRequest.of(requestDto.getPage(), requestDto.getSize(), Sort.by(requestDto.getSort()).descending());
        else
            return PageRequest.of(requestDto.getPage(), requestDto.getSize(), Sort.by(requestDto.getSort()).ascending());
    }

}
