package com.stardust.easyassess.pdm.common;


import com.stardust.easyassess.core.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationProxy {

    @Value("${server.port}")
    private int port;

    @Autowired
    private AuthenticationClient authenticationClient;

    private APIAuthentication authentication;

    private Object lock = new Object();

    private AuthenticationProxy() {
    }

    public void fetch() {
        synchronized (lock) {
            authentication = authenticationClient.register(new PermissionChangeListener("pdm", port, "/authentication/refresh"));
        }
    }

    public APIAuthentication getAuthentication() {
        if (authentication == null) {
            fetch();
        }
        return authentication;
    }

    public RolePermissions getRolePermissions(long role) {
        return authenticationClient.getRolePermissions(role);
    }

    public void saveRolePermissions(RolePermissions rolePermissions) {
        authenticationClient.saveRolePermissions(rolePermissions);
    }

    public boolean isPermitted(String uri, String method) {
        APIAuthentication authentication = getAuthentication();
        for (RolePermissions rolePermissions : authentication.getRoles()) {
            for (Permission permission : rolePermissions.getPermissions()) {
                if (uri.startsWith(permission.getPath())) {
                    return permission.allowed(uri, method);
                }
            }
        }
        return false;
    }
}
