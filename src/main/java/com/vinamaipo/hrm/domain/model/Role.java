package com.vinamaipo.hrm.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role implements GrantedAuthority {

    public static final String USER = "USER";
    //public static final String CONTACT_ADMIN = "CONTACT_ADMIN";
    //public static final String ADDRESS_ADMIN = "ADDRESS_ADMIN";
    public static final String ADMIN = "ADMIN";

    public String authority;
}
