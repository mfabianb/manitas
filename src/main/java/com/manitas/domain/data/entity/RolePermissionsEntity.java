package com.manitas.domain.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Table(name="role_permissions")
@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class RolePermissionsEntity {

    @Id
    @Column(name="id_role_permissions")
    @NotNull
    private Integer idRolePermissions;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_permissions", referencedColumnName = "id_permissions")
    @NotNull
    private PermissionsEntity idPermissions;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    @NotNull
    private RoleEntity idRole;

}