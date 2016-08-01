package com.stardust.easyassess.pdm.models;

import java.io.Serializable;
import java.lang.reflect.Field;

public class DataModel implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean validate() {
        return true;
    }

    public Object get$(String field) {
        try {
            Field f = this.getClass().getDeclaredField(field);
            if (f != null) {
                f.setAccessible(true);
                return f.get(this);
            }
        } catch (Exception e) {}
        return null;
    }
}
