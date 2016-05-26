package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.common.Message;
import com.stardust.easyassess.pdm.common.ResultCode;
import com.stardust.easyassess.pdm.common.Selection;
import com.stardust.easyassess.pdm.common.ViewJSONWrapper;
import com.stardust.easyassess.pdm.models.DataModel;
import com.stardust.easyassess.pdm.services.MaintenanceService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
public abstract class AbstractMaintenanceController<T> extends ActionController {

    protected abstract String getModelName();

    protected MaintenanceService<T> getService() {
        return (MaintenanceService<T>)context.getBean(getModelName() + "Service");
    }

    @RequestMapping(value = "/{id}",
            method={RequestMethod.GET})
    public ViewJSONWrapper get(@PathVariable long id) {
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
                           @RequestParam(value = "filterValue", defaultValue = "") String value ) {

        List<Selection> selections = new ArrayList<Selection>();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));

        if (preList(selections)) {
            return postList(getService().list(page, size , sort, selections));
        } else {
            return createEmptyResult();
        }
    }

    @ResponseBody
    @RequestMapping(value="{id}", method={RequestMethod.PUT})
    public ViewJSONWrapper update(@PathVariable long id,
                            @RequestBody T model) {
        ((DataModel)model).setId(id);
        if (preUpdate(id, model)) {
            return postUpdate(getService().save(model));
        } else {
            return createEmptyResult();
        }
    }

    @ResponseBody
    @RequestMapping(method={RequestMethod.POST})
    public ViewJSONWrapper add(@RequestBody T model) {
        if (preAdd(model)) {
            return postAdd(getService().save(model));
        } else {
            return createEmptyResult();
        }
    }

    @ResponseBody
    @RequestMapping(value="{id}", method={RequestMethod.DELETE})
    public void delete(@PathVariable Long id) {
        if (preDelete(id)) {
            getService().remove(id);
            postDelete(id);
        }
    }

    protected boolean preGet(long id) {
        return true;
    }

    protected ViewJSONWrapper postGet(T model) {
        return new ViewJSONWrapper(model);
    }

    protected boolean preList(List<Selection> selections) {
        return true;
    }

    protected ViewJSONWrapper postList(Page<T> page) {
        return new ViewJSONWrapper(page);
    }

    protected boolean preDelete(long id) {
        return true;
    }

    protected void postDelete(long id) {

    }

    protected boolean preUpdate(long id, T model) {
        return ((DataModel)model).validate();
    }

    protected ViewJSONWrapper postUpdate(T model) {
        return new ViewJSONWrapper(model);
    }

    protected boolean preAdd(T model) {
        return ((DataModel)model).validate();
    }

    protected ViewJSONWrapper postAdd(T model) {
        return new ViewJSONWrapper(model);
    }

    protected ViewJSONWrapper createEmptyResult() {
        return new ViewJSONWrapper(new Message("无记录"), ResultCode.NOT_FOUND);
    }
}
