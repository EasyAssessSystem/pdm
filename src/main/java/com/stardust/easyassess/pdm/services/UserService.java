package com.stardust.easyassess.pdm.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.stardust.easyassess.pdm.dao.router.DataSourceRoute;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.dao.repositories.UserRepository;
import com.stardust.easyassess.pdm.models.User;


@Service("UserService")
@Scope("request")
public class UserService extends EntityService<User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(DataSourceRoute dataSourceRoute) {
        super(dataSourceRoute);
    }

    @Override
    protected DataRepository<User, Long> getRepository() {
        return userRepository;
    }
}
