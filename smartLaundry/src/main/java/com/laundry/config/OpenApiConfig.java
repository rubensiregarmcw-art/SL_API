package com.laundry.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI smartLaundryOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Smart Laundry Management System API")
                        .description("""
                                REST API Documentation for Smart Laundry Management System

                                Features:
                                - JWT Authentication
                                - Multi Tenant Laundry System
                                - Customer Management
                                - Transaction Management
                                - Laundry Service Management
                                - Reporting System
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Smart Laundry Team")
                                .email("support@smartlaundry.com")
                        )
                        .license(new License()
                                .name("Open Source")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://github.com/smartlaundry")
                );
    }
}