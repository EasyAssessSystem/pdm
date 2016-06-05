package com.stardust.easyassess.pdm.dao.repositories;


import com.stardust.easyassess.pdm.models.CodeGroup;

public interface AssayCodeGroupRepository extends DataRepository<CodeGroup, Long> {
    default String getKeyField() {
        return "name";
    }
}
