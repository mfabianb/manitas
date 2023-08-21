package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.QuestionnaireBlankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireBlankRepository extends JpaRepository<QuestionnaireBlankEntity, String> {
}
