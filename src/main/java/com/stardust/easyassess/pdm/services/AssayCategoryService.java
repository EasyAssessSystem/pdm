package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.dao.repositories.AssayCategoryRepository;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.dao.repositories.UserRepository;
import com.stardust.easyassess.pdm.models.AssayCategory;
import com.stardust.easyassess.pdm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("assayCategoryService")
@Scope("request")
public class AssayCategoryService extends EntityService<AssayCategory> {

    @Autowired
    private AssayCategoryRepository assayCategoryRepository;

    @Autowired
    public AssayCategoryService(ViewContext context) {
        super(context);
    }

    @Override
    protected DataRepository<AssayCategory, Long> getRepository() {
        return assayCategoryRepository;
    }
}
