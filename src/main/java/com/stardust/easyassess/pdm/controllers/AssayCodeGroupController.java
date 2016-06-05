package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.models.CodeGroup;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/data/group"})
@EnableAutoConfiguration
public class AssayCodeGroupController extends AbstractMaintenanceController<CodeGroup>  {

    @Override
    protected String getModelName() {
        return "codeGroup";
    }
}
