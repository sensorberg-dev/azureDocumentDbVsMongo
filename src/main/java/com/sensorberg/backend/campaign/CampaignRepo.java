package com.sensorberg.backend.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CampaignRepo {
    @Autowired
    ICampaignRepo repo;

    public CampaignDoc findValidById(String id) {
        return repo.findByDeletedIsFalseAndId(id);
    }

    public CampaignDoc findValidByName(String name) {
        QCampaignDoc campaign = new QCampaignDoc("campaignDoc-" + Thread.currentThread().getName());
        return repo.findOne(campaign.deleted.isFalse().and(campaign.name.eq(name)));
    }

    public CampaignDoc save(CampaignDoc campaignDoc) {
        return repo.save(campaignDoc);
    }

    public List<CampaignDoc> findAll() {
        return repo.findAll();
    }

    public void deleteAll() {
        repo.deleteAll();
    }
}
