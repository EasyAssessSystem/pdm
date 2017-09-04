package com.stardust.easyassess.pdm.services;

import com.stardust.easyassess.pdm.common.OSSBucketAccessor;
import com.stardust.easyassess.pdm.common.ViewContext;
import com.stardust.easyassess.pdm.dao.repositories.DataRepository;
import com.stardust.easyassess.pdm.dao.repositories.HealthMinistryRepository;
import com.stardust.easyassess.pdm.models.HealthMinistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.InputStream;


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

    public String uploadSignature(Long ministryId, InputStream inputStream) {
        HealthMinistry ministry = healthMinistryRepository.findOne(ministryId);
        if (ministry != null) {
            String filename = "signature_" + ministryId + ".png";
            String link = (new OSSBucketAccessor()).put("assess-bucket", "ministry-signature/" + filename, inputStream);
            if (link != null && !link.isEmpty()) {
                ministry.setSignature(link);
                healthMinistryRepository.save(ministry);
            }
            return link;
        }
        return null;
    }

    public String uploadLogo(Long ministryId, String fileType, InputStream inputStream) {
        HealthMinistry ministry = healthMinistryRepository.findOne(ministryId);
        if (ministry != null) {
            String filename = "health_ministry_" + ministryId + "_logo." + fileType;
            String link = (new OSSBucketAccessor()).put("assess-bucket", "ministry-logo/" + filename, inputStream);
            if (link != null && !link.isEmpty()) {
                ministry.setLogo(link);
                healthMinistryRepository.save(ministry);
            }
            return link;
        }
        return null;
    }
}
