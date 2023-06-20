package com.example.obrestdatajpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    //Creamos un bean que representa la documentación de la API
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDetails(){
        Contact contact = new Contact("Warren Sanchez", "https://github.com/Diemendozac", "diemendozac@gmail.com");
        return new ApiInfo("SpringBoot Book API REST",
                "Library API REST Docs",
                "1.0",
                "https://www.google.com"
                , contact,
                "MIT",
                "https://www.google.com",
                Collections.emptyList());
    }
}
