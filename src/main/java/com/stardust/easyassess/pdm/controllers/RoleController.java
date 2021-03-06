package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.models.Role;
import com.stardust.easyassess.pdm.models.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"{domain}/data/role"})
@EnableAutoConfiguration
public class RoleController extends AbstractMaintenanceController<Role>  {

    @Override
    protected String getModelName() {
        return "role";
    }
}
