package com.stardust.easyassess.pdm.controllers;


import com.stardust.easyassess.core.context.ContextSession;
import com.stardust.easyassess.pdm.common.ViewContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ComponentScan({"com.stardust.easyassess.*"})
public class ActionController {


    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    protected HttpServletRequest getRequest() {
        return request;
    }

    protected HttpServletResponse getResponse() {
        return response;
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    protected ContextSession getSession() {
        return applicationContext.getBean(ContextSession.class);
    }
}
