package com.vinamaipo.hrm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest<T> {

    @Valid
    @NotNull
    private Page page;

    @Valid
    @NotNull
    private T query;

    public SearchRequest(T query) {
        this.page = new Page();
        this.query = query;
    }
}
