package com.stardust.easyassess.pdm.dao.repositories;

import com.stardust.easyassess.pdm.models.User;

public interface UserRepository extends DataRepository<User, Long> {
    default String getKeyField() {
        return "username";
    }
}
