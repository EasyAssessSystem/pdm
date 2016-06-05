package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.models.AssayCode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/data/code"})
@EnableAutoConfiguration
public class AssayCodeController extends AbstractMaintenanceController<AssayCode>  {

    @Override
    protected String getModelName() {
        return "assayCode";
    }
}
