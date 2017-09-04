package com.stardust.easyassess.pdm.controllers;

import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.exception.MinistryOnlyException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.pdm.common.ListSelectionBuilder;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.models.HealthMinistry;
import com.stardust.easyassess.pdm.services.HealthMinistryService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"{domain}/data/ministry"})
@EnableAutoConfiguration
public class HealthMinistryController extends AbstractMaintenanceController<HealthMinistry>  {
    private String iqcServiceHost = "http://www.thethirdqc.com/default/iqc/plan/owner";

    private String formServiceHost = "http://www.thethirdqc.com/default/assess/form/owner";

    @RequestMapping(path="/{id}/logo",
            method={RequestMethod.POST})
    public ViewJSONWrapper uploadLogo(@PathVariable Long id, @RequestParam("logo") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            String fileType=file.getOriginalFilename();
            if (fileType.contains(".")) {
                String[] parts = fileType.split("\\.");
                fileType = parts[parts.length - 1];
            }
            String link = ((HealthMinistryService)getService()).uploadLogo(id, fileType, file.getInputStream());
            return new ViewJSONWrapper(link);
        }

        return new ViewJSONWrapper(null);
    }

    @RequestMapping(path="/{id}/signature",
            method={RequestMethod.POST})
    public ViewJSONWrapper uploadSignature(@PathVariable Long id, @RequestParam("signature") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            String link = ((HealthMinistryService)getService()).uploadSignature(id, file.getInputStream());
            return new ViewJSONWrapper(link);
        }

        return new ViewJSONWrapper(null);
    }

    @ResponseBody
    @RequestMapping(value = "/myministry", method = {RequestMethod.PUT})
    public ViewJSONWrapper updateSelfProfile(@RequestBody HealthMinistry model) throws ESAppException {
        Map<String, Object> profile = getUserProfile();
        if (profile != null) {
            List<Long> ministries = (List<Long>) profile.get("ministries");
            if (ministries.contains(model.getId())) {
                if (preUpdate(model.getId(), model)) {
                    return postUpdate(getService().save(model));
                } else {
                    return createEmptyResult();
                }
            } else throw new ESAppException("Permission Denied", 403);
        } else {
            return createEmptyResult();
        }

    }

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
        client.exchange(formServiceHost , HttpMethod.PUT, request, Void.class);
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
                List<Long> ministries = (List<Long>) getUserProfile().get("ministries");
                Map<Long, String> ministryMap = (Map<Long, String>) getUserProfile().get("ministryMap");
                if (ministries != null && ministries.size() > 0 && ministryMap != null) {
                    selections.add(new Selection("id", Selection.Operator.EQUAL, ministries.get(0)));
                }
                return selections;
            }
        });
    }
}
