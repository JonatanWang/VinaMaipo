package com.vinamaipo.hrm.domain.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "customers")
public class Address implements Serializable {

    @Id
    private ObjectId id;

    @CreatedBy
    private ObjectId createdBy;
    @LastModifiedBy
    private ObjectId modifiedBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private String name;

    private Set<ObjectId> employeeIds = new HashSet<>();
}
