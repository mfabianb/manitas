package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="role")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class RoleEntity {
    @Id
    @Column(name="id_role")
    @NotNull
    private Integer idRole;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="enable")
    @NotNull
    private Boolean enable;

}
