package com.manitas.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class UserRequestDto {
    private String idUser;
    private String name;
    private String lastname;
    private String secondLastname;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private LocalDateTime lastLogin;
    private String email;
    private String password;
    private Integer idUserStatus;
    private Integer idRole;
}
