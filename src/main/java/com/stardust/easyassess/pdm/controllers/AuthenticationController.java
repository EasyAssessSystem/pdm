package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.security.RolePermissions;
import com.stardust.easyassess.pdm.common.AuthenticationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping({"authentication","{domain}/data/authentication"})
@EnableAutoConfiguration
public class AuthenticationController {

    @Autowired
    AuthenticationProxy proxy;

    @RequestMapping(value = "/refresh",
            method={RequestMethod.GET})
    public void refresh() throws ESAppException {
        proxy.fetch();
    }

    @ResponseBody
    @RequestMapping(value = "/get/{role}",
            method={RequestMethod.GET})
    public RolePermissions get(@PathVariable long role) throws ESAppException {
        return proxy.getRolePermissions(role);
    }

    @RequestMapping(value = "/update",
            method={RequestMethod.PUT})
    public void update(@RequestBody RolePermissions permissions) throws ESAppException {
        proxy.saveRolePermissions(permissions);
    }
}
