package com.br.todoapi.todo_api.service;

import com.br.todoapi.todo_api.dto.response.FoodResponse;
import com.br.todoapi.todo_api.entity.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodServiceTest {

    private WebClient foodClient;
    private FoodService foodService;

    @BeforeEach
    void setUp() {
        foodClient = mock(WebClient.class, RETURNS_DEEP_STUBS);
        foodService = new FoodService(foodClient);
    }

    @Test
    void deveRetornarListaDeFoodsQuandoApiResponderOK() {
        // Arrange
        Food food = new Food();
        food.setId(1);
        food.setReceita("Frango Xadrez");

        FoodResponse fakeResponse = new FoodResponse();
        fakeResponse.setItems(List.of(food));

        when(foodClient.get()
                .uri("/receitas/todas")
                .retrieve()
                .bodyToMono(FoodResponse.class))
                .thenReturn(Mono.just(fakeResponse));

        // Act
        FoodResponse result = foodService.findFood();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals("Frango Xadrez", result.getItems().get(0).getReceita());
    }

    @Test
    void deveLancarErroQuandoApiRetornarException() {
        when(foodClient.get()
                .uri("/receitas/todas")
                .retrieve()
                .bodyToMono(FoodResponse.class))
                .thenReturn(Mono.error(new WebClientResponseException(
                        500, "Erro", null, null, null
                )));

        assertThrows(WebClientResponseException.class, () -> foodService.findFood());
    }
}
