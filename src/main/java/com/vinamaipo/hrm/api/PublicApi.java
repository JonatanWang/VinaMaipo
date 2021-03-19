package com.vinamaipo.hrm.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Public")
@RestController
@RequestMapping(path = "api/v1/public/all")
public class PublicApi {

    @GetMapping
    public String defaultView() {
        return "Welcome to VinaMaipo!";
    }
}
