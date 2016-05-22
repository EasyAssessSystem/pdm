package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.models.DataModel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/data/{model}"})
@EnableAutoConfiguration
public class MaintenanceController extends AbstractMaintenanceController<DataModel> {


}
