package com.stardust.easyassess.pdm.dao.repositories;


import com.stardust.easyassess.pdm.models.AssayCategory;
import com.stardust.easyassess.pdm.models.AssayCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface AssayCodeRepository extends DataRepository<AssayCode, Long> {
    default String getKeyField() {
        return "code_number";
    }

    @Query("SELECT code FROM AssayCode code WHERE code.group.id =:groupId AND :category MEMBER OF code.categories")
    Page<AssayCode> getAssayCodesByGroupIdAndCategory(Pageable pageable, Long groupId, AssayCategory category);
}
