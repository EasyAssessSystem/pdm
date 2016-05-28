package com.stardust.easyassess.pdm.models;

import java.io.Serializable;

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
}
