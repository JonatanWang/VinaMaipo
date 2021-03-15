package com.vinamaipo.hrm.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactView {

    private String id;

    private UserView creator;
    private LocalDateTime createdAt;

    private String name;
}
