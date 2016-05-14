package com.stardust.easyassess.pdm.dao.router;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.HandlerMapping;

@Component
@Scope("request")
public class HttpDataSourceRoute extends DataSourceRoute {

    @Autowired
    public HttpDataSourceRoute(HttpServletRequest request) {
        super();
        setDomain(request);
        swithContext();
    }

    private void setDomain(HttpServletRequest request) {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        domain = (String) pathVariables.get("domain");
    }

}
