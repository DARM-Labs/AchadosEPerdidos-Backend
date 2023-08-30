package com.darm.ifce.achadoseperdidos.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApi30Config {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agendar - API")
                        .version("1.0")
                        .description("This is the API documentation for the 'Agendar' project that describes the available endpoints and the features provided by our application. " +
                                "The API allows you to interact with our system and perform various operations.")
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .name("BearerAuth")
                                .bearerFormat("JWT")
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY)
                                .scheme("bearer")));
    }
}
