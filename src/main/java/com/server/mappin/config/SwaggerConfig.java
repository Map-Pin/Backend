package com.server.mappin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        String jwtSchemaName = "jwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemaName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemaName, new SecurityScheme()
                        .name(jwtSchemaName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info())
                .addSecurityItem(securityRequirement)
                .components(components);

    }

    private Info info(){
        return new Info()
                .title("Mappin API")
                .description("API 명세서")
                .version("1.0.0");
    }
}
