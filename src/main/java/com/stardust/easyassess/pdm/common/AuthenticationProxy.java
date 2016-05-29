package com.stardust.easyassess.pdm.common;


import com.stardust.easyassess.core.security.*;
import com.stardust.easyassess.pdm.models.Role;

public class AuthenticationProxy {

    private static AuthenticationProxy instance;

    private AuthenticationClient client = new AuthenticationClient("http://127.0.0.1:1337");

    private APIAuthentication authentication;

    private AuthenticationProxy() {
    }

    public void fetch() {
        synchronized (authentication) {
            authentication = client.register(new PermissionChangeListener("pdm", 8180, "/pdm/data/user/1"));
        }
    }

    public static AuthenticationProxy getInstance() {
        if (instance == null) {
            instance = new AuthenticationProxy();
        }
        return instance;
    }

    public APIAuthentication getAuthentication() {
        if (authentication == null) {
            fetch();
        }
        return authentication;
    }

    public boolean isPermitted(String uri, String method, Role role) {
        APIAuthentication authentication = getAuthentication();
        for (RolePermissions rolePermissions : authentication.getRoles()) {
            if (rolePermissions.getRole().equals(role.getId())) {
                for (Permission permission : rolePermissions.getPermissions()) {
                    if (uri.startsWith(permission.getPath())) {
                        return permission.allowed(uri, method);
                    }
                }
            }
        }
        return false;
    }
}
