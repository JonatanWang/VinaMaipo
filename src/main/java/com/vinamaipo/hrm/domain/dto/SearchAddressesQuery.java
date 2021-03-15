package com.vinamaipo.hrm.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SearchAddressesQuery {

    private String id;

    private String creatorId;
    private LocalDateTime createdAtStart;
    private LocalDateTime createAtEnd;

    private String street;

    private String contactId;
    private String contactName;
}
