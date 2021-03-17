package com.vinamaipo.hrm.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditContactRequest {

    @NotNull
    private String name;
}
