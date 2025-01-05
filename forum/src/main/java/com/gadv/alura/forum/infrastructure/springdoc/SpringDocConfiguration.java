package com.gadv.alura.forum.infrastructure.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("gadv.com API")
                        .description("API Rest del desafio de Alura: Foro, que contiene las funcionalidades CRUD de temas(Topics) y respuestas(Responses); ademas de manejar: usuarios, perfiles, y cursos")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@gadv.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://gadv.com/api/licencia")));
    }
}
