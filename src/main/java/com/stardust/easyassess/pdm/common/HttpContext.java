package com.stardust.easyassess.pdm.common;


import com.stardust.easyassess.pdm.dao.router.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Component
@Scope("request")
public class HttpContext extends ViewContext {

    HttpServletRequest request;

    HttpServletResponse response;

    String domain;

    Map pathVariables;

    @Autowired
    public HttpContext(HttpServletRequest request,
                                  HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
    }

    @Override
    protected void init() {
        pathVariables
                = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        domain = (String) pathVariables.get("domain");

        // fill request parameter
        Enumeration pNames = request.getParameterNames();
        while(pNames.hasMoreElements()){
            String name=(String)pNames.nextElement();
            String value=request.getParameter(name);

            parameters.put(name, value);
        }

        switchDomain();
    }


    protected void switchDomain() {
        TenantContext.setCurrentTenant(getDomain());
    }

    public  String getDomain() {
        return domain;
    }
}
