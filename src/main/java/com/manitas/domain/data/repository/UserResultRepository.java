package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.UserResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResultRepository extends JpaRepository<UserResultEntity, String> {
}
