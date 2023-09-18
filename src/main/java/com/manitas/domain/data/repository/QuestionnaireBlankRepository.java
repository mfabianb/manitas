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

@Repository
public interface QuestionnaireBlankRepository extends JpaRepository<QuestionnaireBlankEntity, String> {

    @Query("SELECT q.idInterpellation FROM QuestionnaireBlankEntity q WHERE q.idQuestionnaire = :idQuestionnaire")
    Page<InterpellationEntity> getInterpellationsByIdQuestionnaire(@Param("idQuestionnaire") QuestionnaireEntity idQuestionnaire, Pageable pageable);

}
