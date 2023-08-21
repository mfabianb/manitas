package com.manitas.domain.service;

import com.manitas.domain.data.entity.RoleEntity;
import com.manitas.domain.data.entity.UserStatusEntity;

public interface CatalogService {
    RoleEntity getRoleById(Integer idRole);

    UserStatusEntity getUserStatusById(Integer idUserStatus);
}
