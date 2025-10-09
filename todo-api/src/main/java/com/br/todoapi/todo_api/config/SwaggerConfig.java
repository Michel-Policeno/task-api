package com.br.todoapi.todo_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TO DO LIST API")
                        .version("1.0")
                        .description("API REST para gerenciamento de tarefas")
                        .contact(new Contact()
                                .name("Michel")
                                .email("michel.policeno@hotmail.com")
                                .url("https://github.com/michel-policeno"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}