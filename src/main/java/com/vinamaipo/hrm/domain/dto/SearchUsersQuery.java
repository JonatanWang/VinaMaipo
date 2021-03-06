package com.vinamaipo.hrm.domain.dto;

import lombok.Data;

@Data
public class SearchUsersQuery {

    private String id;
    private String username;
    private String email;
    private String fullname;
}
