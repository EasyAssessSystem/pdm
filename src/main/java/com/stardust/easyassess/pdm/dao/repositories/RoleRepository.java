package com.stardust.easyassess.pdm.dao.repositories;


import com.stardust.easyassess.pdm.models.Role;

public interface RoleRepository extends DataRepository<Role, Long> {
    default String getKeyField() {
        return "name";
    }
}
