package com.vinamaipo.hrm.domain.model;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "addresses")
public class Address implements Serializable {

    @Id
    private ObjectId id;

    @CreatedBy
    private ObjectId creatorId;
    @LastModifiedBy
    private ObjectId modifierId;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    // Home, Job, Favorite etc
    private String label;

    // Storgatan 8, 123 45, Stockholm, Sweden
    private String street;
    private int number;
    private String zip;
    private String city;
    private String province;
    private String country;

//    @Nullable
//    private Set<ObjectId> contactIds = new HashSet<>();
    @Nullable
    private ObjectId contactId;
}
