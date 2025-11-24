package com.br.todoapi.todo_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IngredientesBase {

    private int id;

    @JsonProperty("nomesIngrediente")
    private String[] nomesIngrediente;

    @JsonProperty("receita_id")
    private int receitaId;

    @JsonProperty("created_at")
    private String createdAt;

}
