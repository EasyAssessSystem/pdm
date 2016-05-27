package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.dao.repositories.RoleRepository;
import com.stardust.easyassess.pdm.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("roleService")
@Scope("request")
public class RoleService extends EntityService<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(ViewContext context) {
        super(context);
    }

    @Override
    protected DataRepository<Role, Long> getRepository() {
        return roleRepository;
    }
}
