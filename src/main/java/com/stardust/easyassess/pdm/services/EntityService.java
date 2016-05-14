package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.dao.router.DataSourceRoute;
import com.stardust.easyassess.pdm.dao.DataRepository;
import com.stardust.easyassess.pdm.models.DataModel;

public abstract class EntityService implements BusinessService {

    protected abstract DataRepository<?, Long> getRepository();

    protected DataSourceRoute dataSourceRoute;

    public EntityService(DataSourceRoute httpDataSourceRoute) {
        this.dataSourceRoute = httpDataSourceRoute;
    }

    public DataModel find(long id) {
        DataModel model = (DataModel) getRepository().findOne(id);
        return model;
    }
}
