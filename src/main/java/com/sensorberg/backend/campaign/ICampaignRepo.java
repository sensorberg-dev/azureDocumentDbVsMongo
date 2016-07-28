package com.sensorberg.backend.campaign;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ICampaignRepo extends MongoRepository<CampaignDoc, String>, QueryDslPredicateExecutor<CampaignDoc> {
    CampaignDoc findByDeletedIsFalseAndId(String id);
}
