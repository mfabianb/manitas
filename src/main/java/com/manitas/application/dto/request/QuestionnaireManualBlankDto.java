package com.manitas.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class QuestionnaireManualBlankDto {
    private QuestionnaireEntity questionnaire;
    private List<InterpellationRequestDto> interpellations;
}
