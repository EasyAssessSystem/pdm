package com.stardust.easyassess.pdm.models;

import java.io.Serializable;

public class DataModel implements Serializable {

    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean validate() {
        return true;
    }
}
