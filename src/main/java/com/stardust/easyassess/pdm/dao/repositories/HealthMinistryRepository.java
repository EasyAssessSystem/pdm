package com.stardust.easyassess.pdm.dao.repositories;

import com.stardust.easyassess.pdm.models.HealthMinistry;

public interface HealthMinistryRepository extends DataRepository<HealthMinistry, Long> {
    default String getKeyField() {
        return "name";
    }
}
