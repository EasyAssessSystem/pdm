package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.common.Selection;
import com.stardust.easyassess.pdm.models.User;
import com.stardust.easyassess.pdm.services.EntityService;

import com.stardust.easyassess.pdm.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
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
    public User get(@PathVariable long id) {

        EntityService userService = (EntityService)context.getBean("UserService");
        User u = (User)userService.get(id);
        if (u != null) return u;
        return mockUser;
    }

    @RequestMapping(value = "/key/{username}",
            method={RequestMethod.GET})
    public User get(@PathVariable String username) {

        UserService userService = (UserService)context.getBean("UserService");
        User u = (User)userService.get(username);
        if (u != null) return u;
        return mockUser;
    }


    @RequestMapping(value = "/list",
            method={RequestMethod.GET})
    public Page<User> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "4") Integer size,
                           @RequestParam(value = "sort", defaultValue = "id") String sort,
                           @RequestParam(value = "filterField", defaultValue = "") String field,
                           @RequestParam(value = "filterValue", defaultValue = "") String value ) {


        UserService userService = (UserService)context.getBean("UserService");
        List<Selection> selections = new ArrayList<Selection>();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));

        return userService.list(page, size , sort, selections);
    }


    @RequestMapping(value = "/test",
            method={RequestMethod.GET})
    public Selection test() {

        return new Selection("ID", Selection.Operator.LESS_EQUAL, 1);
    }

    @RequestMapping(value = "/test2",
            method={RequestMethod.GET})
    public Selection test2(@RequestParam(value = "data") Selection selection) {

        return selection;
    }
}
