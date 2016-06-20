package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.core.presentation.*;
import com.stardust.easyassess.core.security.APIAuthentication;
import com.stardust.easyassess.core.security.Permission;
import com.stardust.easyassess.core.security.RolePermissions;
import com.stardust.easyassess.pdm.common.AuthenticationProxy;
import com.stardust.easyassess.pdm.models.Role;
import com.stardust.easyassess.pdm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/data/user"})
@EnableAutoConfiguration
public class UserController extends AbstractMaintenanceController<User>  {

    @Override
    protected String getModelName() {
        return "user";
    }

    @Autowired
    private AuthenticationProxy authenticationProxy;

    @Override
    protected boolean preUpdate(long id, User model) {
        super.preUpdate(id, model);
        User user = getService().get(model.getId());

        if (!user.getPassword().equals(model.getPassword())) {
            model.setPassword(encryption(model.getPassword()));
        }

        return true;
    }

    @Override
    protected boolean preAdd(User model) {
        model.setPassword(encryption(model.getPassword()));
        return true;
    }

    private String encryption(String password) {
        try {
            String hashed = DatatypeConverter.printHexBinary(
                    MessageDigest.getInstance("MD5").digest(password.getBytes("UTF-8")));
            return hashed;
        } catch (Exception e) {

        }
        return password;
    }

    @RequestMapping(value="/session/logoff", method={RequestMethod.GET})
    public ViewJSONWrapper logoff() {
        getSession().remove("currentUser");
        return new ViewJSONWrapper(new Message("退出成功"), ResultCode.SUCC);
    }

    @RequestMapping(value="/session/{username}/{password}", method={RequestMethod.GET})
    public ViewJSONWrapper verify(@PathVariable String username,
                                  @PathVariable String password,
                                  @PathVariable String domain) {
        ViewJSONWrapper jsonWrapper;
        User user = null;

        // hack code here for development usage only
        if (username.equals("adminroot") && password.equals("master")) {
            user = new User();
            user.setId(new Long(0));
            user.setUsername("adminroot");
            user.setPassword(encryption("master"));
            Role role = new Role();
            List<Role> roles = new ArrayList<Role>();
            role.setId(new Long(0));
            roles.add(role);
            user.setRoles(roles);
        } else {
            user = getService().get(username);
        }

        if ((user != null && user.getId() > -1 && user.getPassword().equals(encryption(password)))) {
            APIAuthentication authentication = authenticationProxy.getAuthentication();

            List<RolePermissions> rolePermissionses = new ArrayList<RolePermissions>();

            if (user.getUsername().equals("adminroot")) {
                RolePermissions rolePermissions = authenticationProxy.getRolePermissions(0);
                for (Permission permission : rolePermissions.getPermissions()) {
                    permission.setDELETE(true);
                    permission.setPOST(true);
                    permission.setGET(true);
                    permission.setPUT(true);
                    permission.setUSABILITY(true);
                }
                rolePermissionses.add(rolePermissions);
            } else {
                for (final RolePermissions rp : authentication.getRoles()) {
                    for (final Role role : user.getRoles()) {
                        if (role.getId().equals(rp.getRole())) {
                            rolePermissionses.add(rp);
                        }
                    }
                }
            }
            getSession().put("currentUser", user.getUsername());
            getSession().put("permissions", rolePermissionses);

            Map session = new HashMap<String, Object>();
            session.put("authentication", rolePermissionses);
            session.put("currentUser", user);
            session.put("domain", domain);
            session.put("sessionKey", getSession().getSessionKey());
            jsonWrapper = new ViewJSONWrapper(session);
        } else {
            jsonWrapper = new ViewJSONWrapper(new Message("用户名密码错误", Message.MessageType.ERROR), ResultCode.FAILED);
        }
        return jsonWrapper;
    }
}
