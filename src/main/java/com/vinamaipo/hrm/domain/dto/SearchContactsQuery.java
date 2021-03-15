package com.vinamaipo.hrm.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchContactsQuery {

    private String id;

    private String creatorId;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    private String name;

    private String addressId;
    private String addressStreet;
}
