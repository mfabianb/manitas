package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name="answer")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class AnswerEntity implements Serializable {

    @Id
    @Column(name="id_answer")
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAnswer;

    @Column(name="answer")
    @NotNull
    private String answer;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_media", referencedColumnName = "id_media")
    private MediaEntity idMedia;

    @Column(name="creation_date")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name="modification_date")
    private LocalDateTime modificationDate;

    @Column(name="enable")
    @NotNull
    private Boolean enable;

}