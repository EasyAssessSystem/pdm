package com.stardust.easyassess.pdm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.stardust.easyassess.pdm.dao.router.DataSourceRoute;
import com.stardust.easyassess.pdm.dao.DataRepository;
import com.stardust.easyassess.pdm.dao.UserRepository;

@Service("userservice")
@Scope("request")
public class UserService extends EntityService {

    @Autowired
    private UserRepository repository;

    @Autowired
    public UserService(DataSourceRoute dataSourceRoute) {
        super(dataSourceRoute);
    }

    @Override
    protected DataRepository<?, Long> getRepository() {
        return repository;
    }
}
