package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.models.AssayCategory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"{domain}/data/category"})
@EnableAutoConfiguration
public class AssayCategoryController extends AbstractMaintenanceController<AssayCategory>  {

    @Override
    protected String getModelName() {
        return "assayCategory";
    }
}
