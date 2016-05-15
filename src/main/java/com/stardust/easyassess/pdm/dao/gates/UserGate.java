package com.stardust.easyassess.pdm.dao.gates;


import com.stardust.easyassess.pdm.models.User;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserGate")
public class UserGate extends DataGate<User>{

    protected Class getEntityClass() {
        return User.class;
    }

    protected String getKeyField() {
        return "username";
    }
}
