package com.manitas.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QuestionnaireBlankResponseDto {
    private String idQuestionnaire;
    private String name;
    private String description;
    private Integer length;
    private String topic;

    private List<InterpellationBlankResponseDto> questions;

}
