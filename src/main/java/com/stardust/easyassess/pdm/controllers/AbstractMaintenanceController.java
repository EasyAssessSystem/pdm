package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.common.Selection;
import com.stardust.easyassess.pdm.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
public abstract class AbstractMaintenanceController<T> {

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    protected String getModelName() {
        return (String)((Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).get("model");
    }

    protected MaintenanceService<T> getService() {
        return (MaintenanceService<T>)context.getBean(getModelName() + "Service");
    }

    @RequestMapping(value = "/{id}",
            method={RequestMethod.GET})
    public T get(@PathVariable long id) {
        if (preGet(id)) {
            return postGet(getService().get(id));
        } else {
            return null;
        }
    }


    @RequestMapping(path="/list",
            method={RequestMethod.GET})
    public Page<T> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "4") Integer size,
                           @RequestParam(value = "sort", defaultValue = "id") String sort,
                           @RequestParam(value = "filterField", defaultValue = "") String field,
                           @RequestParam(value = "filterValue", defaultValue = "") String value ) {

        List<Selection> selections = new ArrayList<Selection>();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));

        if (preList(selections)) {
            return postList(getService().list(page, size , sort, selections));
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value="{id}", method={RequestMethod.PUT})
    public T update(@PathVariable long id,
                            @RequestBody T model) {
        if (preUpdate(id, model)) {
            return postUpdate(getService().save(model));
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(method={RequestMethod.POST})
    public T add(@RequestBody T model) {
        if (preAdd(model)) {
            return postAdd(getService().save(model));
        } else {
            return null;
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

    protected T postGet(T model) {
        return model;
    }

    protected boolean preList(List<Selection> selections) {
        return true;
    }

    protected Page<T> postList(Page<T> page) {
        return page;
    }

    protected boolean preDelete(long id) {
        return true;
    }

    protected void postDelete(long id) {

    }

    protected boolean preUpdate(long id, T model) {
        return  true;
    }

    protected T postUpdate(T model) {
        return  model;
    }

    protected boolean preAdd(T model) {
        return  true;
    }

    protected T postAdd(T model) {
        return  model;
    }
}
