package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="interpellation")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class InterpellationEntity implements Serializable {

    @Id
    @Column(name="id_interpellation")
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idInterpellation;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_question", referencedColumnName = "id_question")
    private QuestionEntity idQuestion;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_answer", referencedColumnName = "id_answer")
    private AnswerEntity idAnswer;

    @Column(name="correct")
    @NotNull
    private Boolean correct;

    @Column(name="enable")
    @NotNull
    private Boolean enable;

    @Column(name = "interpellation_key")
    private String interpellationKey;

}
