package com.stardust.easyassess.pdm.aspects;


import com.stardust.easyassess.core.security.APIAuthentication;
import com.stardust.easyassess.pdm.common.AuthenticationProxy;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthenticationAspect {

    private static Logger logger = Logger.getLogger(LogAspect.class);

    public AuthenticationAspect() {

    }

    @Pointcut("execution(* com.stardust.easyassess.pdm.controllers.*Controller.*(..))")
    public void controllerRequest() {
    }

    @Before("controllerRequest()")
    public void doBefore(JoinPoint joinPoint) throws Throwable  {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();

        //AuthenticationProxy.getInstance().isPermitted(uri, method, null);

    }
}