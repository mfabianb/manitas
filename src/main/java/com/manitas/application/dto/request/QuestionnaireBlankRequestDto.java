package com.manitas.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class QuestionnaireBlankRequestDto {
    private String idQuestionnaire;
    private String questionnaireBlankKey;

    private String questionnaireName;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Integer length;
    private String topics;
}
