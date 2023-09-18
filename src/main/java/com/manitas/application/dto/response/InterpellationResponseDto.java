package com.manitas.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manitas.domain.data.entity.QuestionEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class InterpellationResponseDto {
    private String idInterpellation;
    private QuestionEntity question;
    private List<AnswerResponseDto> answers;
}