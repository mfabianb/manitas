package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class InterpellationKey implements Serializable {
    private String idInterpellation;
    private String idQuestion;
    private String idAnswer;
}
