package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="questionnaire_answered")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class QuestionnaireAnsweredEntity implements Serializable {

    @Id
    @Column(name="id_questionnaire_answered")
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idQuestionnaireAnswered;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @NotNull
    private UserEntity idUser;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_questionnaire_blank", referencedColumnName = "id_questionnaire_blank")
    @NotNull
    private QuestionnaireBlankEntity idQuestionnaireBlank;

    @Column(name="user_answer")
    @NotNull
    private String userAnswer;

    @Column(name = "blank_key")
    private String blankKey;

    @Column(name = "answered_key")
    private String answeredKey;

}
