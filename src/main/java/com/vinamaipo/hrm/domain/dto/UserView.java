package com.vinamaipo.hrm.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserView {

    private String id;
    private String username;
    @Email
    private String email;
    private String fullName;
}
