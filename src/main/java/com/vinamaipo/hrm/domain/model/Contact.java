package com.vinamaipo.hrm.domain.model;

import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "employees")
@Data
@Builder
public class Contact implements Serializable {

    @Id
    private ObjectId id;

    @CreatedBy
    private ObjectId createdBy;
    @LastModifiedBy
    private ObjectId modifiedBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private  LocalDateTime modifiedAt;

    private String name;

    @Nullable
    private Set<ObjectId> customerIds = new HashSet<>();
}