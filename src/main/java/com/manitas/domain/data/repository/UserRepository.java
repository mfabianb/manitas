package com.manitas.domain.data.repository;

import com.manitas.application.dto.response.UserResponseDto;
import com.manitas.domain.data.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findUserByEmail(String email);
    Optional<UserEntity> findUserByIdUser(String idUser);

    @Query("SELECT u FROM UserEntity u WHERE " +
            "(u.email LIKE %:email%) " +
            "AND (u.name LIKE %:name%) " +
            "AND (u.lastname LIKE %:lastname%) " +
            "AND (u.secondLastname LIKE %:secondLastname%) " +
            "AND (DATE(u.creationDate) = DATE(:creationDate) OR :creationDate IS NULL) " +
            "AND (DATE(u.modificationDate) = DATE(:modificationDate) OR :modificationDate IS NULL) " +
            "AND (DATE(u.lastLogin) = DATE(:lastLogin) OR :lastLogin IS NULL) " +
            "AND (u.idRole.idRole IN (:idRole)) " +
            "AND (u.idUserStatus.idUserStatus IN (:idUserStatus))")
    Page<UserEntity> findPageByFilter(@Param("email") String email,
                                      @Param("name") String name,
                                      @Param("lastname") String lastname,
                                      @Param("secondLastname") String secondLastname,
                                      @Param("creationDate") LocalDateTime creationDate,
                                      @Param("modificationDate") LocalDateTime modificationDate,
                                      @Param("lastLogin") LocalDateTime lastLogin,
                                      @Param("idRole") Integer idRole,
                                      @Param("idUserStatus") Integer idUserStatus,
                                      Pageable pageable);

    @Query("SELECT new com.manitas.application.dto.response.UserResponseDto(u.idUser, u.name, u.lastname, " +
            "u.secondLastname, u.creationDate, u.modificationDate, u.email, u.lastLogin, " +
            "u.idUserStatus.idUserStatus, u.idUserStatus.name, u.idRole.idRole, u.idRole.name) FROM UserEntity u WHERE " +
            "(u.email LIKE %:email%) " +
            "AND (u.name LIKE %:name%) " +
            "AND (u.lastname LIKE %:lastname%) " +
            "AND (u.secondLastname LIKE %:secondLastname%) " +
            "AND (DATE(u.creationDate) = DATE(:creationDate) OR :creationDate IS NULL) " +
            "AND (DATE(u.modificationDate) = DATE(:modificationDate) OR :modificationDate IS NULL) " +
            "AND (DATE(u.lastLogin) = DATE(:lastLogin) OR :lastLogin IS NULL) " +
            "AND (u.idRole.idRole IN (:idRole)) " +
            "AND (u.idUserStatus.idUserStatus IN (:idUserStatus))")
    Page<UserResponseDto> findPageDtoByFilter(@Param("email") String email,
                                              @Param("name") String name,
                                              @Param("lastname") String lastname,
                                              @Param("secondLastname") String secondLastname,
                                              @Param("creationDate") LocalDateTime creationDate,
                                              @Param("modificationDate") LocalDateTime modificationDate,
                                              @Param("lastLogin") LocalDateTime lastLogin,
                                              @Param("idRole") Integer idRole,
                                              @Param("idUserStatus") Integer idUserStatus,
                                              Pageable pageable);
}
