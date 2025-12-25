package com.example;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info().title("REST Spring Boot Customer Management System")
                        .description("A RESTful API application built with Spring Boot for managing customer information with MySQL database integration.")
                        .version("1.0.0")
        );
    }
}

