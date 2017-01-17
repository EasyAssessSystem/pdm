package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.pdm.common.ListSelectionBuilder;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.models.HealthMinistry;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"{domain}/data/ministry"})
@EnableAutoConfiguration
public class HealthMinistryController extends AbstractMaintenanceController<HealthMinistry>  {
    private String iqcServiceHost = "http://127.0.0.1:9290/default/iqc/plan/owner";

    @Override
    protected String getModelName() {
        return "healthMinistry";
    }

    @Override
    protected ViewJSONWrapper postUpdate(HealthMinistry model) throws ESAppException {
        RestTemplate client = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "SESSION=" + getSession().getSessionKey());
        HttpEntity<HealthMinistry> request = new HttpEntity(model, requestHeaders);
        client.exchange(iqcServiceHost , HttpMethod.PUT, request, Void.class);
        return super.postUpdate(model);
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
