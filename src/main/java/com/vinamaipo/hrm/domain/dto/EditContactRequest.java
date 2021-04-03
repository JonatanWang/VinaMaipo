package com.vinamaipo.hrm.domain.dto;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class EditContactRequest {

    @NotNull
    private String name;

    @Nullable
    private Set<ObjectId> addressIds;
}
