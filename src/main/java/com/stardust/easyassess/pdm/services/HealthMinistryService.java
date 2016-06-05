package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.dao.repositories.HealthMinistryRepository;
import com.stardust.easyassess.pdm.models.HealthMinistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("healthMinistryService")
@Scope("request")
public class HealthMinistryService extends EntityService<HealthMinistry> {

    @Autowired
    private HealthMinistryRepository healthMinistryRepository;

    @Autowired
    public HealthMinistryService(ViewContext context) {
        super(context);
    }

    @Override
    protected DataRepository<HealthMinistry, Long> getRepository() {
        return healthMinistryRepository;
    }

    @Override
    public HealthMinistry save(HealthMinistry model) {
        if (model.getId() > 0) {
            HealthMinistry prev = getRepository().findOne(model.getId());
            model.setSupervisor(prev.getSupervisor());
            for (HealthMinistry ministry : prev.getMinistries()) {
                if (!model.getMinistries().contains(ministry)) {
                    ministry.setSupervisor(null);
                }
            }
        }

        return getRepository().save(model);
    }
}
