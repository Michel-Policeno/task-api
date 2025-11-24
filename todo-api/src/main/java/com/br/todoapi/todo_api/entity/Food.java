package com.br.todoapi.todo_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private int id;
    private String receita;
    private String ingredientes;
    @JsonProperty("modo_preparo")
    private String modoPreparo;
    @JsonProperty("link_imagem")
    private String linkImagem;
    private String tipo;
    @JsonProperty("created_at")
    private String created;
    @JsonProperty("IngredientesBase")
    private List<IngredientesBase> ingredientesBase;

  }
