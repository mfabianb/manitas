package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.QuestionnaireEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity, String> {

    @Query("SELECT q FROM QuestionnaireEntity q WHERE ( " +
            "(q.name LIKE %:questionnaireName%) " +
            "AND (q.description LIKE %:description%) " +
            "AND (DATE(q.creationDate) = DATE(:creationDate) OR :creationDate IS NULL) " +
            "AND (DATE(q.modificationDate) = DATE(:modificationDate) OR :modificationDate IS NULL) " +
            "AND (q.length = :length OR :length = 0 )" +
            "AND (q.topic LIKE %:topics% OR :topics IS NULL)) ")
    Page<QuestionnaireEntity> getQuestionnairePageByFilter(@Param("questionnaireName") String questionnaireName,
                                                           @Param("description") String description,
                                                           @Param("creationDate") LocalDateTime creationDate,
                                                           @Param("modificationDate") LocalDateTime modificationDate,
                                                           @Param("length") Integer length,
                                                           @Param("topics") String topics,
                                                           Pageable pageable);

}
