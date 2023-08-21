package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<AnswerEntity, String> {
}
