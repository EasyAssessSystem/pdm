package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.pdm.common.ListSelectionBuilder;
import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.models.AssayCode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"{domain}/data/code"})
@EnableAutoConfiguration
public class AssayCodeController extends AbstractMaintenanceController<AssayCode>  {

    @Override
    protected String getModelName() {
        return "assayCode";
    }

    @Override
    protected void initSelectionBuilders() {
        listSelectionBuilders.put("categorized", new ListSelectionBuilder() {
            @Override
            public List<Selection> buildSelections(List<Selection> selections, ViewContext context) {
                selections.add(new Selection("group.id", Selection.Operator.EQUAL, context.getLong("group_id")));
                return selections;
            }
        });
    }
}
