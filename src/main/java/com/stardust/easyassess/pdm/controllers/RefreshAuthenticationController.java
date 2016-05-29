package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.common.AuthenticationProxy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping({"authentication"})
@EnableAutoConfiguration
public class RefreshAuthenticationController  {
    @RequestMapping(value = "/refresh",
            method={RequestMethod.GET})
    public void refresh() {
        AuthenticationProxy.getInstance().fetch();
    }
}
