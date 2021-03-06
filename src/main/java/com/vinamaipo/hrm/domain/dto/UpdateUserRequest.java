package com.vinamaipo.hrm.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UpdateUserRequest {

    @NotBlank
    private String fullname;
    private Set<String> authorities;
}
