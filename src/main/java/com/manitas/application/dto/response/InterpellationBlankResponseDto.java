package com.manitas.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manitas.domain.data.entity.MediaEntity;
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
public class InterpellationBlankResponseDto {
    private String idInterpellation;
    private String interpellationKey;
    private String idQuestionnaireBlank;

    private String idQuestion;
    private String question;
    private MediaEntity idMedia;
    private Integer idTopic;
    private String nameTopic;

    private List<AnswerBlankResponseDto> options;
}
