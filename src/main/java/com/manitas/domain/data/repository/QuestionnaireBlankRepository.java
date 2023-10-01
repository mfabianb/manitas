package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.InterpellationEntity;
import com.manitas.domain.data.entity.QuestionnaireBlankEntity;
import com.manitas.domain.data.entity.QuestionnaireEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireBlankRepository extends JpaRepository<QuestionnaireBlankEntity, String> {

    @Query("SELECT q.idInterpellation FROM QuestionnaireBlankEntity q WHERE q.idQuestionnaire = :idQuestionnaire")
    Page<InterpellationEntity> getInterpellationsByIdQuestionnaire(@Param("idQuestionnaire") QuestionnaireEntity idQuestionnaire, Pageable pageable);

    @Query("SELECT q FROM QuestionnaireBlankEntity q WHERE q.idQuestionnaire = :idQuestionnaire")
    List<QuestionnaireBlankEntity> getInterpellationsByBlankKey(@Param("idQuestionnaire") QuestionnaireEntity idQuestionnaire);

    @Query("SELECT q FROM QuestionnaireBlankEntity q WHERE q.blankKey = :blankKey")
    List<QuestionnaireBlankEntity> getInterpellationsByBlankKey(@Param("blankKey") String blankKey);

    @Query("SELECT q FROM QuestionnaireBlankEntity q WHERE q.idQuestionnaireBlank = :idQuestionnaireBlank")
    Optional<QuestionnaireBlankEntity> findBlankById(@Param("idQuestionnaireBlank")String idQuestionnaireBlank);

    @Query("SELECT COUNT(q) FROM QuestionnaireBlankEntity q WHERE q.blankKey = :blankKey")
    long countInterpellationsBlankByBlankKey(@Param("blankKey")String blankKey);

}
