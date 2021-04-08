package com.vinamaipo.hrm.domain.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "contacts")
@Data
@Builder
public class Contact implements Serializable {

    @Id
    private ObjectId id;

    @CreatedBy
    private ObjectId userId;
    @LastModifiedBy
    private ObjectId modifierId;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private  LocalDateTime modifiedAt;

    private String name;

//    @Nullable
//    private Set<ObjectId> addressIds;
}
