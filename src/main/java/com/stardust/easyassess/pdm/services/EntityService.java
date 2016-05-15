package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.Selection;
import com.stardust.easyassess.pdm.dao.router.DataSourceRoute;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.models.DataModel;
import com.stardust.easyassess.pdm.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityService<T> implements MaintenanceService<T> {

    protected abstract DataRepository<T, Long> getRepository();

    protected DataSourceRoute dataSourceRoute;

    public EntityService(DataSourceRoute httpDataSourceRoute) {
        this.dataSourceRoute = httpDataSourceRoute;
    }

    public T get(Long id) {
        return getRepository().findOne(id);
    }

    public T get(String key) {
        return getRepository().findBykey(key);
    }

    public Page<T> list(int page, int size, String sortBy) {
        return list(page, size, sortBy, new ArrayList<Selection>());
    }

    public Page<T> list(int page, int size, String sortBy, List<Selection> selections) {
        Sort sort = new Sort(Sort.Direction.ASC, sortBy);
        Pageable pageable = new PageRequest(page, size, sort);
        return getRepository().findAll(pageable, selections);
    }
}
