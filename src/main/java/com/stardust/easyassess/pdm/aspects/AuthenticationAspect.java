package com.stardust.easyassess.pdm.aspects;


import com.stardust.easyassess.core.context.ContextSession;
import com.stardust.easyassess.core.presentation.Message;
import com.stardust.easyassess.core.presentation.ResultCode;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.pdm.common.AuthenticationProxy;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Order(1)
public class AuthenticationAspect {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private AuthenticationProxy authenticationProxy;

    private static Logger logger = Logger.getLogger(LogAspect.class);

    public AuthenticationAspect() {

    }

    @Pointcut("execution(* com.stardust.easyassess.pdm.controllers.*Controller.*(..))")
    public void controllerRequest() {
    }

    @Around("controllerRequest()")
    public Object aroundControllerRequest(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        ContextSession session = applicationContext.getBean(ContextSession.class);
        if (!pjp.toString().contains("com.stardust.easyassess.pdm.controllers.UserController.verify")
                && session.get("userProfile") == null) {
            session.clear();
            clearCookie();
            result = new ViewJSONWrapper(new Message("503"), ResultCode.FAILED);
        } else {
            result = pjp.proceed();
        }
        return result;
    }

    @Before("controllerRequest()")
    public void doBefore(JoinPoint joinPoint) throws Throwable  {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//
//        ContextSession session = applicationContext.getBean(ContextSession.class);
//        Map profile = (Map)session.get("userProfile", null);
//        List<RolePermissions> rolePermissionses
//                = (List<RolePermissions>)
//                    session.get("authentication", new ArrayList<RolePermissions>());
//
//        if (profile == null || (Long)profile.get("user_id") <=0) {
//            throw new Exception("无效的会话,请重新登录");
//        }

        // authenticationProxy.isPermitted(uri, method, user.getRoles());
    }

    @After("controllerRequest()")
    public void doAfter(JoinPoint joinPoint) throws Throwable  {

    }

    @AfterThrowing(pointcut = "controllerRequest()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {

    }

    private void clearCookie() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletResponse response = sra.getResponse();
        Cookie cookie = new Cookie("SESSION", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}