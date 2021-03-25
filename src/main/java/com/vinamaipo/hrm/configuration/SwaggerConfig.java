package com.vinamaipo.hrm.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ConditionalOnProperty(prefix = "spring",value = {"profiles.active"},havingValue = "dev")
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi
                .builder()
                .group("Api")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(
                        new Info()
                                .title("Contact-Address RESTful API")
                                .description("Rest Api for contact-address web application")
                                .version("1.0")
                );
    }
}
