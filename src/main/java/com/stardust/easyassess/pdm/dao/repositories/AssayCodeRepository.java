package com.stardust.easyassess.pdm.dao.repositories;


import com.stardust.easyassess.pdm.models.AssayCode;

public interface AssayCodeRepository extends DataRepository<AssayCode, Long> {
    default String getKeyField() {
        return "code_number";
    }
}
