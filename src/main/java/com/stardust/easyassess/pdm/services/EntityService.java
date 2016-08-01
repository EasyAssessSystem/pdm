package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.exceptions.DuplicatedKeyException;
import com.stardust.easyassess.pdm.models.DataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityService<T> implements MaintenanceService<T> {

    protected abstract DataRepository<T, Long> getRepository();

    protected ViewContext context;

    public EntityService(ViewContext context) {
        this.context = context;
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

    public T save(T model) throws DuplicatedKeyException {
        DataModel dataModel = (DataModel)model;
        String keyField = getRepository().getKeyField();
        if (dataModel.getId().compareTo(new Long(0)) < 0
                && keyField != null
                && !keyField.isEmpty()) {
            Object keyVal = dataModel.get$(keyField);
            if (keyVal != null) {
                T m = this.get(keyVal.toString());
                if (m != null && ((DataModel)m).getId().compareTo(new Long(0)) > 0) {
                    throw new DuplicatedKeyException(keyVal.toString());
                }
            }
        }

        return getRepository().save(model);
    }

    public void remove(T model) {
        getRepository().delete(model);
    }

    public void remove(Long id) {
        getRepository().delete(id);
    }

}
