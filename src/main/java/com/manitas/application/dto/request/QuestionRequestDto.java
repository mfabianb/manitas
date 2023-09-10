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
public class QuestionRequestDto {
    private String idQuestion;
    private String question;
    private MediaRequestDto media;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Boolean enable;
    private Integer idTopic;
}
