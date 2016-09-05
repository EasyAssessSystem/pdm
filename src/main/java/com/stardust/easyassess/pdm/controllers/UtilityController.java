package com.stardust.easyassess.pdm.controllers;


import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.pdm.models.User;
import com.stardust.easyassess.pdm.services.DataImportService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping({"/utilities"})
@EnableAutoConfiguration
public class UtilityController extends ActionController {
    @ResponseBody
    @RequestMapping(value = "/import/ministries", method = {RequestMethod.GET})
    public ViewJSONWrapper importUsers(@RequestParam String file) throws ESAppException {
        DataImportService service = getApplicationContext().getBean(DataImportService.class);
        Map<Integer, Object> results = service.proceed(file);
        return new ViewJSONWrapper(results);
    }
}
