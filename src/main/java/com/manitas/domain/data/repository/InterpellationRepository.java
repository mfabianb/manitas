package com.manitas.domain.data.repository;

import com.manitas.domain.data.entity.InterpellationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterpellationRepository extends JpaRepository<InterpellationEntity, String> {
    @Query("SELECT i FROM InterpellationEntity i WHERE i.interpellationKey = :id")
    List<InterpellationEntity> getAllByInterpellationKey(String id);

    @Query("SELECT i FROM InterpellationEntity i WHERE " +
            "(i.idQuestion.question like %:question%) " +
            "AND (DATE(i.idQuestion.creationDate) = DATE(:creationDate) OR :creationDate IS NULL)" +
            "AND (i.idQuestion.idMedia.type = :type OR :type IS NULL) " +
            "AND (i.idQuestion.idTopic.enable = :topicEnable  OR :topicEnable IS NULL) " +
            "AND (i.idQuestion.enable = :enable OR :enable IS NULL) ")
    Page<InterpellationEntity> findAllEntityPage(@Param("question") String question,
                                                 @Param("creationDate") LocalDateTime creationDate,
                                                 @Param("type") Integer type,
                                                 @Param("topicEnable") Boolean topicEnable,
                                                 @Param("enable") Boolean enable,
                                                 Pageable pageable);

    List<InterpellationEntity> findAllByInterpellationKey(String interpellationKey);

    @Query("SELECT i FROM InterpellationEntity i WHERE i.idQuestion.idTopic.idTopic IN (:idTopics) AND i.idQuestion.enable = true ORDER BY i.interpellationKey")
    Page<InterpellationEntity> findAllByIdTopics(@Param("idTopics") List<Integer> idTopics, Pageable pageable);
}
