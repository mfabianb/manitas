package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name="user_result")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class UserResultEntity implements Serializable {

    @Id
    @Column(name="id_user_result")
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idUserResult;

    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @Column(name="score")
    private Integer score;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @NotNull
    private UserEntity idUser;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_questionnaire", referencedColumnName = "id_questionnaire")
    @NotNull
    private QuestionnaireEntity idQuestionnaire;

    @Column(name = "answered_key")
    private String answeredKey;

}
