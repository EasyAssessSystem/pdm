package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.dao.repositories.AssayCodeGroupRepository;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.models.CodeGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("codeGroupService")
@Scope("request")
public class AssayCodeGroupService extends EntityService<CodeGroup> {

    @Autowired
    private AssayCodeGroupRepository assayCodeGroupRepository;

    @Autowired
    public AssayCodeGroupService(ViewContext context) {
        super(context);
    }

    @Override
    protected DataRepository<CodeGroup, Long> getRepository() {
        return assayCodeGroupRepository;
    }
}
