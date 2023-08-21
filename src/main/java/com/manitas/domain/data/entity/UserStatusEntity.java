package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Table(name="user_status")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class UserStatusEntity {
    @Id
    @Column(name="id_user_status")
    @NotNull
    private Integer idUserStatus;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="description")
    private String description;
}
