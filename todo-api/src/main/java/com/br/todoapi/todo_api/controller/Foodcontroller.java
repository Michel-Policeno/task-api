package com.br.todoapi.todo_api.controller;

import com.br.todoapi.todo_api.dto.response.FoodResponse;
import com.br.todoapi.todo_api.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foods/")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "foods", description = "Endpoints para chama API publica - https://api-receitas-pi.vercel.app")

public class Foodcontroller {

    private final FoodService foodService;

    @GetMapping
    @Operation(summary = "Buscar receitas", description = "Retorna a quantidade de receitas solicitadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receitas retornadas"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<FoodResponse> allFood () {
        FoodResponse foodList = foodService.findFood();
        return ResponseEntity.ok(foodList);
    }
}
