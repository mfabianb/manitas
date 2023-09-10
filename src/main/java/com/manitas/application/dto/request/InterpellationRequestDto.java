package com.manitas.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class InterpellationRequestDto {
    private String idInterpellation;
    private QuestionRequestDto question;
    private List<AnswerRequestDto> answers;
    private Boolean enable;
}
