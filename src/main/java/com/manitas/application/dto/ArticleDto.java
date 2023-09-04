package com.manitas.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class ArticleDto {
    private String id;
    private String name;
    private String description;
    private String info;
    private String media;
    private String email;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Boolean enable;
    private Integer idTopic;
}
