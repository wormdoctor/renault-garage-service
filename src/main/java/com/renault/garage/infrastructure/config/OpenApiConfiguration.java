package com.renault.garage.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI renaultGarageOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Renault Garage Management API")
                        .description("API for managing Renault garages, vehicles, and accessories")
                        .version("v1.0.0"));
    }
}
