package com.sanvalero.mylibrary.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyLibraryDocConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("MyLibrary API")
                        .description("API REST sobre una librería")
                        .contact(new Contact()
                                    .name("Aritz Iribarren")
                                    .email("a24898@svalero.com")
                                    .url("https://github.com/aritzie/mylibrary"))
                        .version("1.0"));
    }
}
