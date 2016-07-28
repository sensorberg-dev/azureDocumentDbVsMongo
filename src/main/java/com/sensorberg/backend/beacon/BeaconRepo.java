package com.sensorberg.backend.beacon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeaconRepo {
    @Autowired
    IBeaconRepo repo;

    public BeaconDoc findValidById(String id) {
        return repo.findByDeletedIsFalseAndId(id);
    }

    public BeaconDoc findValidByName(String name) {
        QBeaconDoc beacon = new QBeaconDoc("beaconDoc-" + Thread.currentThread().getName());
        return repo.findOne(beacon.deleted.isFalse().and(beacon.name.eq(name)));
    }

    public BeaconDoc findOne(String id) {
        return repo.findOne(id);
    }

    public boolean exists(String id) {
        return repo.exists(id);
    }

    public BeaconDoc save(BeaconDoc beaconDoc) {
        return repo.save(beaconDoc);
    }

    public List<BeaconDoc> findAll() {
        return repo.findAll();
    }

    public void deleteAll() {
        repo.deleteAll();
    }
}
