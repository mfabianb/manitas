package com.manitas.domain.data.repository;

import com.manitas.application.dto.response.ArticleResponseDto;
import com.manitas.domain.data.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {

    @Query("SELECT a FROM ArticleEntity a WHERE " +
            "(a.name like %:name%) " +
            "AND (a.description like %:description%) " +
            "AND (a.info like %:info%) " +
            "AND (DATE(a.creationDate) = DATE(:creationDate) OR :creationDate IS NULL) " +
            "AND (DATE(a.modificationDate) = DATE(:modificationDate) OR :modificationDate IS NULL) " +
            "AND (a.idUser.email like %:email%) " +
            "AND (a.idTopic.idTopic IN (:idTopic)) " +
            "AND (a.enable = :enable OR :enable IS NULL) ")
    Page<ArticleEntity> getArticlePage(@Param("name") String name,
                                       @Param("description") String description,
                                       @Param("info") String info,
                                       @Param("creationDate") LocalDateTime creationDate,
                                       @Param("modificationDate") LocalDateTime modificationDate,
                                       @Param("email") String email,
                                       @Param("idTopic") Integer idTopic,
                                       @Param("enable") Boolean enable,
                                       Pageable pageable);

    @Query("SELECT new com.manitas.application.dto.response.ArticleResponseDto(a.idArticle, " +
            "a.name, a.description, a.info, a.idMedia, a.creationDate, a.modificationDate, a.enable, " +
            "a.idTopic.idTopic, a.idTopic.name, a.idUser.idUser, a.idUser.name, a.idUser.lastname, " +
            "a.idUser.secondLastname, a.idUser.email) FROM ArticleEntity a WHERE " +
            "(a.name like %:name%) " +
            "AND (a.description like %:description%) " +
            "AND (a.info like %:info%) " +
            "AND (DATE(a.creationDate) = DATE(:creationDate) OR :creationDate IS NULL) " +
            "AND (DATE(a.modificationDate) = DATE(:modificationDate) OR :modificationDate IS NULL) " +
            "AND (a.idUser.email like %:email%) " +
            "AND (a.idTopic.idTopic IN (:idTopic)) " +
            "AND (a.enable = :enable OR :enable IS NULL) ")
    Page<ArticleResponseDto> getArticleDtoPage(@Param("name") String name,
                                               @Param("description") String description,
                                               @Param("info") String info,
                                               @Param("creationDate") LocalDateTime creationDate,
                                               @Param("modificationDate") LocalDateTime modificationDate,
                                               @Param("email") String email,
                                               @Param("idTopic") Integer idTopic,
                                               @Param("enable") Boolean enable,
                                               Pageable pageable);

}
