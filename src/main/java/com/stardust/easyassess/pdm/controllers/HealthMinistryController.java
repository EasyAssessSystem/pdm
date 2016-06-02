package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.models.HealthMinistry;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

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

}
