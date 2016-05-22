package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.ViewContext;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.dao.repositories.UserRepository;
import com.stardust.easyassess.pdm.models.User;


@Service("userService")
@Scope("request")
public class UserService extends EntityService<User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(ViewContext context) {
        super(context);
    }

    @Override
    protected DataRepository<User, Long> getRepository() {
        return userRepository;
    }
}
