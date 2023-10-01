package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.QuestionnaireAnsweredEntity;
import com.manitas.domain.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionnaireAnsweredRepository extends JpaRepository<QuestionnaireAnsweredEntity, String> {
    @Query("SELECT q FROM QuestionnaireAnsweredEntity q WHERE q.idUser = :idUser AND q.blankKey = :blankKey AND q.answeredKey = :answeredKey")
    List<QuestionnaireAnsweredEntity> findByIdUserAndBlankKeyAndAnsweredKey(@Param("blankKey")String blankKey,
                                                                            @Param("answeredKey")String answeredKey,
                                                                            @Param("idUser") UserEntity idUser);
}
