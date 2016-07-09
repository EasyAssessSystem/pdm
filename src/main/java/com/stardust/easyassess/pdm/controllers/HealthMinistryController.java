package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.pdm.common.ListSelectionBuilder;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.models.HealthMinistry;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    protected void initSelectionBuilders() {
        listSelectionBuilders.put("unassigned", new ListSelectionBuilder() {
            @Override
            public List<Selection> buildSelections(List<Selection> selections, ViewContext context) {
                selections.add(new Selection("supervisor.id", Selection.Operator.IS_NULL));
                selections.add(new Selection("id", Selection.Operator.NOT_EQUAL, context.getLong("for")));
                return selections;
            }
        });

        listSelectionBuilders.put("affiliated", new ListSelectionBuilder() {
            @Override
            public List<Selection> buildSelections(List<Selection> selections, ViewContext context) {
                List<Long> ministries = (List<Long>)getUserProfile().get("ministries");
                Map<Long, String> ministryMap = (Map<Long, String>)getUserProfile().get("ministryMap");
                if (ministries != null && ministries.size() > 0 && ministryMap != null) {
                    selections.add(new Selection("id", Selection.Operator.EQUAL, ministries.get(0)));
                }
                return selections;
            }
        });
    }
}
