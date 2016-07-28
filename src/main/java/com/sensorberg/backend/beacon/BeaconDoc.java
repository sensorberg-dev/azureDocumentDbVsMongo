package com.sensorberg.backend.beacon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Document
@lombok.Data
@lombok.experimental.Accessors(chain = true)
public class BeaconDoc {
    @Id
    private String id;

    @Version
    @JsonIgnore
    private Long version;

    @JsonIgnore
    private String updatedBy;

    @JsonIgnore
    private Boolean deleted;

    @JsonIgnore
    private String owner;

    private Instant createdAt;

    private Instant updatedAt;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
}
