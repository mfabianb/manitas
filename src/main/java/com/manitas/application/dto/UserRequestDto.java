package com.manitas.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
    private String email;
    private String password;
    private Integer idUserStatus;
    private Integer idRole;
}
