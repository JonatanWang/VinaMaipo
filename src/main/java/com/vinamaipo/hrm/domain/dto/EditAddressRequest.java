package com.vinamaipo.hrm.domain.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EditAddressRequest {

    private List<@NotNull String> contactIds;

    @NotBlank
    private String street;
    private int number;
    private String zip;
    private String city;
    private String province;
    private String country;
}
