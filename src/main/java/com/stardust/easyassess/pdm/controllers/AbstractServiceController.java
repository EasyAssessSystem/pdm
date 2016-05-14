package com.stardust.easyassess.pdm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class AbstractServiceController {

    @Autowired
    ApplicationContext context;
}
