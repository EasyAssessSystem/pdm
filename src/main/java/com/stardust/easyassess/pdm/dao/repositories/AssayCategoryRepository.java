package com.stardust.easyassess.pdm.dao.repositories;


import com.stardust.easyassess.pdm.models.AssayCategory;

public interface AssayCategoryRepository extends DataRepository<AssayCategory, Long> {
    default String getKeyField() {
        return "name";
    }

    AssayCategory getAssayCategoryByName(String name);
}
