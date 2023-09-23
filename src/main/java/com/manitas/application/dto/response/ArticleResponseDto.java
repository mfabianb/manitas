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
public class ArticleResponseDto {
    private String idArticle;
    private String name;
    private String description;
    private String info;
    private MediaEntity idMedia;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Boolean enable;
    private Integer idTopic;
    private String nameTopic;
    private String idAuthor;
    private String authorName;
    private String authorLastname;
    private String authorSecondLastname;
    private String authorEmail;
}
