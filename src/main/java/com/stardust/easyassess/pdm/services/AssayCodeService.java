package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.dao.repositories.AssayCodeRepository;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.models.AssayCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("assayCodeService")
@Scope("request")
public class AssayCodeService extends EntityService<AssayCode> {

    @Autowired
    private AssayCodeRepository assayCodeRepository;

    @Autowired
    public AssayCodeService(ViewContext context) {
        super(context);
    }

    @Override
    protected DataRepository<AssayCode, Long> getRepository() {
        return assayCodeRepository;
    }
}
