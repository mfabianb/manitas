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
public class ReplyQuestionnaireFromUserRequestDto {
    private String idUser;
    private String blankKey;
    private List<InterpellationReplyRequestDto> answers;
}
