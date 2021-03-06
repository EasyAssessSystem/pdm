package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.*;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.pdm.common.ListSelectionBuilder;
import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.exceptions.DuplicatedKeyException;
import com.stardust.easyassess.pdm.models.DataModel;
import com.stardust.easyassess.pdm.services.MaintenanceService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
public abstract class AbstractMaintenanceController<T> extends ActionController {

    protected abstract String getModelName();

    protected Map<String, ListSelectionBuilder> listSelectionBuilders = new HashMap<String, ListSelectionBuilder>();

    protected MaintenanceService<T> getService() {
        return (MaintenanceService<T>)getApplicationContext().getBean(getModelName() + "Service");
    }


    @PostConstruct
    public void init() {
        this.initSelectionBuilders();
    }


    protected void initSelectionBuilders() {

    }

    @RequestMapping(value = "/{id}",
            method={RequestMethod.GET})
    public ViewJSONWrapper get(@PathVariable long id) throws ESAppException {
        if (preGet(id)) {
            return postGet(getService().get(id));
        } else {
            return createEmptyResult();
        }
    }


    @RequestMapping(path="/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "4") Integer size,
                           @RequestParam(value = "sort", defaultValue = "id") String sort,
                           @RequestParam(value = "filterField", defaultValue = "") String field,
                           @RequestParam(value = "filterValue", defaultValue = "") String value ) throws ESAppException {

        return this.list(page, size, sort, field, value , null);
    }

    @RequestMapping(path="/list/{subset}",
            method={RequestMethod.GET})
    public ViewJSONWrapper list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "4") Integer size,
                                @RequestParam(value = "sort", defaultValue = "id") String sort,
                                @RequestParam(value = "filterField", defaultValue = "") String field,
                                @RequestParam(value = "filterValue", defaultValue = "") String value,
                                @PathVariable String subset) throws ESAppException {

        List<Selection> selections = new ArrayList<Selection>();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));

        if (subset != null && !subset.isEmpty() && listSelectionBuilders.containsKey(subset)) {
            listSelectionBuilders.get(subset).buildSelections(selections, getApplicationContext().getBean(ViewContext.class));
        }

        if (preList(selections)) {
            return postList(getService().list(page, size , sort, selections));
        } else {
            return createEmptyResult();
        }
    }

    @ResponseBody
    @RequestMapping(value="{id}", method={RequestMethod.PUT})
    public ViewJSONWrapper update(@PathVariable long id,
                            @RequestBody T model) throws ESAppException {
        ((DataModel)model).setId(id);
        if (preUpdate(id, model)) {
            return postUpdate(getService().save(model));
        } else {
            return createEmptyResult();
        }
    }

    @ResponseBody
    @RequestMapping(method={RequestMethod.POST})
    public ViewJSONWrapper add(@RequestBody T model) throws ESAppException {
        if (preAdd(model)) {
            return postAdd(getService().save(model));
        } else {
            return createEmptyResult();
        }
    }

    @ResponseBody
    @RequestMapping(value="{id}", method={RequestMethod.DELETE})
    public void delete(@PathVariable Long id) throws ESAppException {
        if (preDelete(id)) {
            getService().remove(id);
            postDelete(id);
        }
    }

    protected boolean preGet(long id) throws ESAppException {
        return true;
    }

    protected ViewJSONWrapper postGet(T model) throws ESAppException {
        return new ViewJSONWrapper(model);
    }

    protected boolean preList(List<Selection> selections) throws ESAppException {
        return true;
    }

    protected ViewJSONWrapper postList(Page<T> page) throws ESAppException {
        return new ViewJSONWrapper(page);
    }

    protected boolean preDelete(long id) throws ESAppException {
        return true;
    }

    protected void postDelete(long id) throws ESAppException {

    }

    protected boolean preUpdate(long id, T model) throws ESAppException {
        return ((DataModel)model).validate();
    }

    protected ViewJSONWrapper postUpdate(T model) throws ESAppException {
        return new ViewJSONWrapper(model);
    }

    protected boolean preAdd(T model) throws ESAppException {
        return ((DataModel)model).validate();
    }

    protected ViewJSONWrapper postAdd(T model) throws ESAppException {
        return new ViewJSONWrapper(model);
    }

    protected ViewJSONWrapper createEmptyResult() {
        return new ViewJSONWrapper(new Message("无记录"), ResultCode.NOT_FOUND);
    }

    protected Map<String, Object> getUserProfile() {
        Map<String, Object> profile = (Map<String, Object>)getSession().get("userProfile");
        if (profile == null) {
            profile = new HashMap<String, Object>();
        }

        return profile;
    }
}
