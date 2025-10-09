package com.br.todoapi.todo_api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateRequest {

    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @Min(value = 0, message = "Prioridade mínima é 0")
    @Max(value = 2, message = "Prioridade máxima é 2")
    private Integer prioridade;
}