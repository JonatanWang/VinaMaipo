package com.vinamaipo.hrm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role implements GrantedAuthority {

    public static final String USER_ADMIN = "USER_ADMIN";
    public static final String EMPLOYEE_ADMIN = "EMPLOYEE_ADMIN";
    public static final String CUSTOMER_ADMIN = "CUSTOMER_ADMIN";

    public String authority;
}
