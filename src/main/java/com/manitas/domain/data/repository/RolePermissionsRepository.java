package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.RolePermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionsRepository extends JpaRepository<RolePermissionsEntity, Integer> {
}
