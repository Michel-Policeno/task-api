package com.br.todoapi.todo_api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("foodClient")
    public WebClient foodClient() {
        return WebClient.builder()
                .baseUrl("https://api-receitas-pi.vercel.app")
                .build();
    }
}
