package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name="questionnaire")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class QuestionnaireEntity implements Serializable {

    @Id
    @Column(name="id_questionnaire")
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idQuestionnaire;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="description")
    @NotNull
    private String description;

    @Column(name="creation_date")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name="modification_date")
    private LocalDateTime modificationDate;

    @Column(name="length")
    private Integer length;

    @Column(name="topic")
    private String topic;

    @Column(name="enable")
    @NotNull
    private Boolean enable;

}