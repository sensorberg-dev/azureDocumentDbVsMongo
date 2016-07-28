package com.sensorberg.backend.campaign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sensorberg.backend.beacon.BeaconDoc;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Document
@lombok.Data
@lombok.experimental.Accessors(chain = true)
public class CampaignDoc {
    @Id
    private String id;

    @Version
    @JsonIgnore
    private Long version;

    private Instant createdAt;

    private Instant updatedAt;

    @JsonIgnore
    private String updatedBy;

    @JsonIgnore
    private Boolean deleted;

    @JsonIgnore
    private String owner;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @DBRef
    private List<BeaconDoc> beacons;
}
