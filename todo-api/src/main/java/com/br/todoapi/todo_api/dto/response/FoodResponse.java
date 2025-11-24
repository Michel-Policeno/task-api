package com.br.todoapi.todo_api.dto.response;

import com.br.todoapi.todo_api.entity.Food;
import lombok.Data;

import java.util.List;

@Data
public class FoodResponse {
    private List<Food> items;
}
