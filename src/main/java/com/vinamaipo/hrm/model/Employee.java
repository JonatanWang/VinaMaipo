package com.vinamaipo.hrm.model;

import com.mongodb.lang.Nullable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
@Data
@Builder
public class Employee {

    @Id
    private String id;
    private String name;
    @Nullable
    private String customer;
}
