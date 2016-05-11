package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.dao.UserRepository;
import com.stardust.easyassess.pdm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @RequestMapping(value = "/{id}",
                    method={RequestMethod.GET})

    public User get(@PathVariable long id) {
        User u = repository.findOne(id);
        return u;
    }
}
