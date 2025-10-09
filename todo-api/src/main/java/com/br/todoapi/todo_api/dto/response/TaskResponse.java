package com.br.todoapi.todo_api.dto.response;

import com.br.todoapi.todo_api.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Integer prioridade;
    private LocalDateTime dataCriacao;
    private Boolean realizado;
    private LocalDateTime dataRealizacao;
    private LocalDateTime ultimaModificacao;
    private Boolean ativo;

   public TaskResponse(Task task) {
        this.id = task.getId();
        this.nome = task.getNome();
        this.descricao = task.getDescricao();
        this.prioridade = task.getPrioridade();
        this.dataCriacao = task.getDataCriacao();
        this.realizado = task.getRealizado();
        this.dataRealizacao = task.getDataRealizacao();
        this.ultimaModificacao = task.getUltimaModificacao();
        this.ativo = task.getAtivo();
    }

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(task);
    }
}