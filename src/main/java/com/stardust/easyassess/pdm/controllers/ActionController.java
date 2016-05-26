package com.stardust.easyassess.pdm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ComponentScan({"com.stardust.easyassess.*"})
public class ActionController {


    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

}
