package com.manitas.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manitas.domain.data.entity.MediaEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class AnswerResponseDto {
    private String idAnswer;
    private String answer;
    private MediaEntity idMedia;
    private LocalDateTime creationDate;
    private Boolean enable;
    private Boolean correct;
}
