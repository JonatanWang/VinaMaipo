package com.vinamaipo.hrm.domain.dto;

import com.vinamaipo.hrm.domain.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CreateUserRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
    private String fullname;

    @NotBlank
    private String password;

    private String rePassword;
    private Set<String> authorities;
}
