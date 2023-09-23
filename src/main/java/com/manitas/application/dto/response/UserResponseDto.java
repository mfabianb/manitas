package com.manitas.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class UserResponseDto {
    private String idUser;
    private String name;
    private String lastname;
    private String secondLastname;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String email;
    private LocalDateTime lastLogin;

    private Integer idUserStatus;
    private String nameStatus;

    private Integer idRole;
    private String nameRole;
}
