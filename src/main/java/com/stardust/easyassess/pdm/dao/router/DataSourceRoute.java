package com.stardust.easyassess.pdm.dao.router;


public class DataSourceRoute {

    protected String domain;


    protected void swithContext() {
        TenantContext.setCurrentTenant(getDomain());
    }

    public  String getDomain() {
        return domain;
    }
}
