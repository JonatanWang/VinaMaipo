package com.vinamaipo.hrm.domain.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Document(collection = "users")
public class User implements UserDetails, Serializable {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String username;
    @Indexed
    private String fullname;
    @Indexed(unique = true)
    @Email
    private String email;
    private String password;

    private Set<Role> authorities = new HashSet<>();
    private boolean enabled = true;

    private List<Contact> contacts = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = true;
    }
    /**
    public User(String username, String email, String fullname, String password) {
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.enabled = true;
    }


    public User(String username, String email, String fullname, String password, String[] authorities) {
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        for (String s: authorities) {
            System.out.println(s);
            this.setAuthorities(Set.of(new Role(s)));
        }
        this.enabled = true;
    }
     */

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}
