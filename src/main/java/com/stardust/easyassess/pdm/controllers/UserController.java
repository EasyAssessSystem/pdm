package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.models.User;
import com.stardust.easyassess.pdm.services.EntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "{domain}/user")
@EnableAutoConfiguration
@ImportResource("classpath:/conf/spring/service-context.xml")
public class UserController {


    @Autowired
    ApplicationContext context;

    @Autowired
    User mockUser;

    @RequestMapping(value = "/{id}",
            method={RequestMethod.GET})
    public User get(@PathVariable String domain, @PathVariable long id) {

        EntityService userService = (EntityService)context.getBean("userservice");
        User u = (User)userService.find(id);
        if (u != null) return u;
        return mockUser;
    }
}
