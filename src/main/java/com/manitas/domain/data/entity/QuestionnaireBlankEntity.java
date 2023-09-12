package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="questionnaire_blank")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class QuestionnaireBlankEntity implements Serializable {

    @Id
    @Column(name="id_questionnaire_blank")
    @NotNull
    private String idQuestionnaireBlank;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_questionnaire", referencedColumnName = "id_questionnaire")
    @NotNull
    private QuestionnaireEntity idQuestionnaire;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumns({
            @JoinColumn(name = "id_interpellation", referencedColumnName = "id_interpellation"),
            @JoinColumn(name = "id_question", referencedColumnName = "id_question"),
            @JoinColumn(name = "id_answer", referencedColumnName = "id_answer")
    })
    @NotNull
    private InterpellationEntity idInterpellation;

}
