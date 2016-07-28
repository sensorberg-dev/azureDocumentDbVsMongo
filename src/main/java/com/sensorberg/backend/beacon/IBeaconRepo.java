package com.sensorberg.backend.beacon;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface IBeaconRepo extends MongoRepository<BeaconDoc, String>, QueryDslPredicateExecutor<BeaconDoc> {
    BeaconDoc findByDeletedIsFalseAndId(String id);
}
