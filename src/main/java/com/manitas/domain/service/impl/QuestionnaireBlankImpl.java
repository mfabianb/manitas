package com.manitas.domain.service.impl;

import com.manitas.domain.service.InterpellationService;
import com.manitas.domain.service.QuestionnaireBlankService;
import com.manitas.domain.service.QuestionnaireService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuestionnaireBlankImpl implements QuestionnaireBlankService {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private InterpellationService interpellationService;

    public void createQuestionnaire(){}

}
