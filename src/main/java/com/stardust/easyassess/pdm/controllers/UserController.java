package com.stardust.easyassess.pdm.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"{domain}/data/user"})
@EnableAutoConfiguration
//@ImportResource("classpath:/conf/spring/service-context.xml")
public class UserController extends MaintenanceController {

    @Override
    protected String getModelName() {
        return "user";
    }
}
