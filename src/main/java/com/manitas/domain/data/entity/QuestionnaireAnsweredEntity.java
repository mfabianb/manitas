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
    @JoinColumns({
            @JoinColumn(name = "id_interpellation", referencedColumnName = "id_interpellation"),
            @JoinColumn(name = "id_question", referencedColumnName = "id_question"),
            @JoinColumn(name = "id_answer", referencedColumnName = "id_answer")
    })
    @NotNull
    private InterpellationEntity idInterpellation;

    @Column(name="user_answer")
    @NotNull
    private Boolean userAnswer;

}
