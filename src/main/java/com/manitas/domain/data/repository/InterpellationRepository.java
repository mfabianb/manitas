package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.InterpellationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterpellationRepository extends JpaRepository<InterpellationEntity, String> {
    @Query("SELECT i FROM InterpellationEntity i WHERE i.idInterpellation = :id")
    List<InterpellationEntity> getAllById(String id);
}
