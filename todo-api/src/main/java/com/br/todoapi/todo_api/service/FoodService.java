package com.br.todoapi.todo_api.service;

import com.br.todoapi.todo_api.dto.response.FoodResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class FoodService {
    private final WebClient foodClient;

    public FoodService(@Qualifier("foodClient") WebClient foodClient) {
        this.foodClient = foodClient;
    }

    public FoodResponse findFood(){
        return foodClient
                .get()
                .uri("/receitas/todas")
                .retrieve()
                .bodyToMono(FoodResponse.class)
                .block();
    }
}
