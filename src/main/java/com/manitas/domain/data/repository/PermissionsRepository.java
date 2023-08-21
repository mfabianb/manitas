package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<PermissionsEntity, Integer> {
}
