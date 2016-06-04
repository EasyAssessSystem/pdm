package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.common.Selection;
import com.stardust.easyassess.pdm.common.ViewJSONWrapper;
import com.stardust.easyassess.pdm.models.HealthMinistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/data/ministry"})
@EnableAutoConfiguration
public class HealthMinistryController extends AbstractMaintenanceController<HealthMinistry>  {
    @Override
    protected String getModelName() {
        return "healthMinistry";
    }

    @Override
    protected boolean preUpdate(long id, HealthMinistry model) {
        super.preUpdate(id, model);
        for (HealthMinistry child : model.getMinistries()) {
            child.setSupervisor(model);
            getService().save(child);
        }
        return true;
    }

    @Override
    protected boolean preAdd(HealthMinistry model) {
        for (HealthMinistry child : model.getMinistries()) {
            child.setSupervisor(model);
        }
        return true;
    }

    @RequestMapping(path="/list/unassigned",
            method={RequestMethod.GET})
    public ViewJSONWrapper unassigned(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "4") Integer size,
                                @RequestParam(value = "sort", defaultValue = "id") String sort,
                                @RequestParam(value = "filterField", defaultValue = "") String field,
                                @RequestParam(value = "filterValue", defaultValue = "") String value ) {

        List<Selection> selections = new ArrayList<Selection>();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        selections.add(new Selection("supervisor.id", Selection.Operator.IS_NULL));

        if (preList(selections)) {
            return postList(getService().list(page, size , sort, selections));
        } else {
            return createEmptyResult();
        }
    }
}
