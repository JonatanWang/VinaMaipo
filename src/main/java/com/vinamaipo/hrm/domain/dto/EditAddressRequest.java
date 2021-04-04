package com.vinamaipo.hrm.domain.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EditAddressRequest {

    private ObjectId contactId;

    @NotBlank
    private String street;
    private int number;
    private String zip;
    private String city;
    private String province;
    private String country;

}
